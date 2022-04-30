const canvas = document.getElementById('canvas');
const increaseBtn = document.getElementById('increase');
const decreaseBtn = document.getElementById('decrease');
const sizeEL = document.getElementById('size');
const colorEl = document.getElementById('color');
const clearEl = document.getElementById('clear');

const ctx = canvas.getContext('2d');

let size = 10
let isPressed = false
let color = "white"
let x
let y

canvas.addEventListener('mousedown', (e) => {
    isPressed = true

    x = e.offsetX
    y = e.offsetY
})

document.addEventListener('mouseup', (e) => {
    isPressed = false

    x = undefined
    y = undefined
})

canvas.addEventListener('mousemove', (e) => {
    if(isPressed) {
        const x2 = e.offsetX
        const y2 = e.offsetY
        drawCircle(x2, y2)
        drawLine(x, y, x2, y2)
        x = x2
        y = y2
    }
})

function drawCircle(x, y) {
    ctx.beginPath();
    ctx.arc(x, y, size, 0, Math.PI * 2)
    ctx.fillStyle = color
    ctx.fill()
}

function drawLine(x1, y1, x2, y2) {
    ctx.beginPath()
    ctx.moveTo(x1, y1)
    ctx.lineTo(x2, y2)
    ctx.strokeStyle = color
    ctx.lineWidth = size * 2
    ctx.stroke()
}
$("#submit").click(function (e) { 
    e.preventDefault();
    var myCanvas = $(document).find('#canvas');
    var image = myCanvas.get(0).toDataURL();
    // console.log($("#inputK").val());
    $.ajax({
        type: "post",
        url: "postImg",
        data: {"imageBase64":image,"inputK":$("#inputK").val()},
        dataType: "json",
        timeout: 10000,
        async: false,
        error: function(){
            console.log("WOOPS");
        },
        success: function (response) {
            console.log(response.msg);
            let w = response.msg.split(", ");
            console.log(w);
            let result=w[0].split("=");
            if(result[1]/$("#inputK").val()>0.5){
                $("#result").css("font-size", 150);
                $("#result").html(result[0]);
            }else
            {            
                $("#result").css("font-size", 80);
                $("#result").html("Not Found");
            }
        }
    });
});




// function updateSizeOnScreen() {
//     sizeEL.innerText = size
// }

// increaseBtn.addEventListener('click', () => {
//     size += 5

//     if(size > 50) {
//         size = 50
//     }

//     updateSizeOnScreen()
// })

// decreaseBtn.addEventListener('click', () => {
//     size -= 5

//     if(size < 5) {
//         size = 5
//     }

//     updateSizeOnScreen()
// })

// colorEl.addEventListener('change', (e) => color = e.target.value)

clearEl.addEventListener('click', () => ctx.clearRect(0,0, canvas.width, canvas.height))
