package com.JoinUs.dp.domain.mail.service;

import com.JoinUs.dp.domain.mail.dto.RequestSendEmailDto;
import com.JoinUs.dp.domain.mail.dto.RequestVerificationCodeDto;
import com.JoinUs.dp.domain.mail.repository.MailRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailRepository mailRepository;


    /**
     * 인증 이메일 발송
     */
    // MailService
    public void sendEmail(RequestSendEmailDto dto) {
        String code = generateRandomCode();
        sendHtmlEmail(dto.getEmail(), createEmailContent(code));
        mailRepository.saveCode(dto.getEmail(), code);
    }

    /**
     * 인증코드 HTML 이메일 내용 생성
     */
    private String createEmailContent(String verificationCode) {
        return """
                <html>
                    <body>
                        <h1>인증 코드: %s</h1>
                        <p>해당 코드를 회원가입 창에 입력하세요.</p>
                        <footer style='color: grey; font-size: small;'>
                            <p>본 메일은 자동응답 메일이므로 본 메일에 회신하지 마시기 바랍니다.</p>
                        </footer>
                    </body>
                </html>
                """.formatted(verificationCode);
    }

    /**
     * HTML 이메일 전송
     */
    private void sendHtmlEmail(String toEmail, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom("이메일 보류", "서비스명"); // 나중에 도메인 메일로 교체
            helper.setTo(toEmail);
            helper.setSubject("이메일 인증 번호");
            helper.setText(content, true);

            javaMailSender.send(mimeMessage);
        } catch (UnsupportedEncodingException | jakarta.mail.MessagingException e) {
            throw new RuntimeException("메일 전송 실패", e);
        }
    }

    /**
     * 랜덤 인증 코드 생성 (6자리 알파벳+숫자)
     */
    public String generateRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    /**
     * 인증 코드 검증
     */
    public boolean verifyCode(RequestVerificationCodeDto dto) {
        return mailRepository.verifyCode(dto.getEmail(), dto.getCode());
    }

}
