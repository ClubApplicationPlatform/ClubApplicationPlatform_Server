function likeClub(button, clubName) {
    const liked = button.classList.toggle('liked');
    button.textContent = liked ? '♥' : '♡';

    if (liked) {
        showToast(`${clubName} 알림을 설정했어요!`);
    } else {
        showToast(`${clubName} 알림을 취소했어요.`);
    }
}

function showToast(message) {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.classList.add('show');
    setTimeout(() => {
        toast.classList.remove('show');
    }, 2000);
}
function likeClub(button, clubName) {
    const liked = button.classList.toggle('liked');
    button.textContent = liked ? '♥' : '♡';

    if (liked) {
        // 백엔드로 찜 요청 보내기
        fetch('/clubs/like', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name: clubName })
        })
            .then(response => response.text())
            .then(data => {
                console.log('서버 응답:', data);
                showToast(`${clubName} 알림을 설정했어요!`);
            })
            .catch(err => {
                console.error('에러 발생:', err);
                showToast('서버에 찜 요청을 보내는 중 문제가 발생했어요.');
            });
    } else {
        showToast(`${clubName} 알림을 취소했어요.`);
        // 취소 요청을 보내는 로직 필요하면 추가
    }
}
