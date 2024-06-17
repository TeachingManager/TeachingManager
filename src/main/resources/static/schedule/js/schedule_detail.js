function deleteScheduleRequest(){
    let id = document.getElementById('schedule-id').value;
    fetch(`/api/Schedule/Detail/${id}`, {method : 'POST'})
    .then(()=>{
        alert('일정 삭제 완료!');
        location.replace('/Schedule');
    });
}