// 수정
const modifyButton = document.getElementById('modify-btn');
// 등록
const submitButton = document.getElementById('submit-btn');

if(modifyButton){
    modifyButton.addEventListener('click', event =>{
        let params  = new URLSearchParams(location.search);
        let pk = parseInt(params.get('pk'));

        fetch(`/api/Schedule/Detail/${pk}`,{
            method : 'PUT',
            headers:{
                "Content-Type" : "application/json",
            },
            body: JSON.stringify({
                title : document.getElementById('title').value,
                start_date : document.getElementById('start_date').value,
                end_date : document.getElementById('end_date').value,
                memo : document.getElementById('memo').value,
            })
        })
        .then(()=>{
                alert('수정 완료!');
                location.replace(`/Schedule/Detail?pk=${pk}`);
        })
    })
}

if (submitButton){
    submitButton.addEventListener('click', event =>{

        fetch(`/api/Schedule`,{
            method : 'POST',
            headers:{
                "Content-Type" : "application/json",
            },
            body: JSON.stringify({
                title : document.getElementById('title').value,
                start_date : document.getElementById('start_date').value,
                end_date : document.getElementById('end_date').value,
                memo : document.getElementById('memo').value,
            })
        }).then(response=>{
                  if (!response.ok) {
                    throw new Error('Network response was not ok');
                  }
                  return response.json();
              })
              .then(data => {
                  alert('등록 완료!');
                  console.log(data);
                  location.replace(`/Schedule/Detail?pk=${data.schedule_id}`);
              })
              .catch(error => {
                console.error('등록과정에서의 오류 발생함', error);
              });
    })
}