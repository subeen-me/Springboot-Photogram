// (1) 회원정보 수정
function update(userId, event) {
    event.preventDefault(); //form 태그 액션을 막기 (더 이상 진행되지 않계)
    let data = $("#profileUpdate").serialize(); //key=value
    console.log(data);

    $.ajax({
        type: "put",
        url: `/api/user/${userId}`,
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res=>{ //HttpStatus 상태코드 200번대
        console.log("update 성공", res);
        location.href = `/user/${userId}`;
    }).fail(error=>{ //HttpStatus 상태코드 200번대가 아닐 때
        if(error.data==null) {
            alert(error.responseJSON.message);
        }
         else {
            alert(JSON.stringify(error.responseJSON.data)); // 자바스크립트 오브젝트를 Json으로 변경
            console.log("update 실패", error.data);
        }
    });

}