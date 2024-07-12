const canvas = document.querySelector('canvas');
const ctx = canvas.getContext('2d');
const lineWidth = document.getElementById('line_width');
const color = document.getElementById('text_color');
const backColor = document.getElementById('background_color');
const resetBtn = document.getElementById('reset');
const inputImage = document.getElementById('file');
const save = document.getElementById('save');
const drawing = document.getElementById('drawing');
const eraser = document.getElementById('eraser');
const brush = document.getElementById('brush');
const canvasWidth = document.getElementById('canvas_width');
const canvasHeight = document.getElementById('canvas_height');
const square = document.getElementById('square');
const triangle = document.getElementById('triangle');
const circle = document.getElementById('circle');
canvas.width = canvasWidth.value;
canvas.height = canvasHeight.value;
ctx.lineWidth = lineWidth.value;

let cPushArray = [];
let cStep = -1;
let rectangles = [];

let isPainting = false;
let isDrawing = false;
let isBrushing = false;
let isErasing = false;
let issquare = false;

const textInput = document.getElementById('textInput');  // Changed from input to textarea

const textColorPicker = document.getElementById('textColorPicker');
const textSizeInput = document.getElementById('textSizeInput');
const fontWeightSelect = document.getElementById('fontWeightSelect');
const fontFamilySelect = document.getElementById('fontFamilySelect');
const addTextButton = document.getElementById('addTextButton');

let texts = [];
let isDragging = false;
let isDragging2 = false;

let selectedTextIndex = -1;
let selectedRectIndex = -1;

let resizeHandleSize = 10;
let backgroundImage = null;

let brushStrokes = [];
let penStrokes = [];

function setBackgroundImage(imageUrl) {
    const img = new Image();

    //    img.crossOrigin = 'anonymous'; // CORS 설정
    img.src = `/ai/imageone/${imageUrl}`;
    img.onload = function () {
        backgroundImage = img;
        // 이미지가 설정될 때마다 펜 스트로크와 브러시 스트로크를 지우고 다시 그립니다.
        redrawCanvas();
        inputImage.value = null;
    };
}

function onImg(event) {
    const files = event.target.files[0];
    const formData = new FormData();
    formData.append('image', files);

    $.ajax({
        url: '/uploadImage',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function (response) {
            console.log('Image uploaded successfully');
            // 서버로부터의 응답에 따른 작업을 수행할 수 있음
            const url = URL.createObjectURL(files);
            const img = new Image();
            img.src = url;
            img.onload = function () {
                backgroundImage = img;
                redrawCanvas();
                inputImage.value = null;
            };
        },
        error: function (xhr, status, error) {
            console.error('Error uploading image:', error);
            // 에러 처리를 수행할 수 있음
        }
    });
}

function addTextToCanvas() {
    const text = textInput.value;
    const textColor = textColorPicker.value;
    const textSize = textSizeInput.value;
    const fontWeight = fontWeightSelect.value;
    const fontFamily = fontFamilySelect.value;

    texts.push({
        text: text,
        color: textColor,
        size: textSize,
        weight: fontWeight,
        font: fontFamily,
        x: 50,
        y: 50
    });
    redrawCanvas();
}

function redrawCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.setTransform(1, 0, 0, 1, 0, 0);

    if (backgroundImage) {
        ctx.drawImage(backgroundImage, 0, 0, canvas.width, canvas.height);
    }

    penStrokes.forEach(stroke => {
        ctx.save();
        ctx.beginPath();
        ctx.moveTo(stroke.points[0].x, stroke.points[0].y);
        for (let i = 1; i < stroke.points.length; i++) {
            ctx.lineTo(stroke.points[i].x, stroke.points[i].y);
        }
        ctx.strokeStyle = stroke.color;
        ctx.lineWidth = stroke.width;
        ctx.stroke();
        ctx.restore();
    });

    brushStrokes.forEach(stroke => {
        ctx.save();
        ctx.globalAlpha = stroke.alpha;
        ctx.fillStyle = stroke.color;
        ctx.beginPath();
        ctx.arc(stroke.x, stroke.y, stroke.radius, 0, Math.PI * 2);
        ctx.fill();
        ctx.restore();
    });

    texts.forEach(textObj => {
        const lines = textObj.text.split('\n');
        ctx.fillStyle = textObj.color;
        ctx.font = `${textObj.weight} ${textObj.size}px ${textObj.font}`;
        const lineHeight = textObj.size * 1.2;
        lines.forEach((line, index) => {
            ctx.fillText(line, textObj.x, textObj.y + (index * lineHeight));
        });
    });

    rectangles.forEach(rect => {
        ctx.save();
        ctx.beginPath();
        ctx.strokeStyle = rect.color;
        ctx.lineWidth = rect.lineWidth;
        ctx.rect(rect.x, rect.y, rect.width, rect.height);
        ctx.stroke();
        ctx.restore();

        // Draw resize handle
        ctx.save();
        ctx.fillStyle = 'red';
        ctx.fillRect(rect.x + rect.width - resizeHandleSize / 2, rect.y + rect.height - resizeHandleSize / 2, resizeHandleSize, resizeHandleSize);
        ctx.restore();
    });
}

function isMouseOnRectangle(mouseX, mouseY, rect) {
    console.log("asdfasdf");
    const padding = 1; // 클릭 범위를 확장하기 위한 여유 공간
    return mouseX >= rect.x - padding && mouseX <= rect.x + rect.width + padding &&
        mouseY >= rect.y - padding && mouseY <= rect.y + rect.height + padding;
}

function isMouseOnResizeHandle(mouseX, mouseY, rect) {
    return mouseX >= rect.x + rect.width - resizeHandleSize && mouseX <= rect.x + rect.width + resizeHandleSize &&
        mouseY >= rect.y + rect.height - resizeHandleSize && mouseY <= rect.y + rect.height + resizeHandleSize;
}



// 텍스트 클릭 확인
function isMouseOnText(mouseX, mouseY, textObj) {
    const padding = 10; // 클릭 범위를 확장하기 위한 여유 공간
    const textWidth = ctx.measureText(textObj.text).width;
    const lines = textObj.text.split('\n');
    const lineHeight = textObj.size * 1.2; // Adjust line height as needed
    const totalTextHeight = lines.length * lineHeight;

    return mouseX >= textObj.x - padding && mouseX <= textObj.x + textWidth + padding &&
        mouseY >= textObj.y - textObj.size - padding && mouseY <= textObj.y + totalTextHeight + padding;
}

// 캔버스에서 클릭 이벤트 처리
canvas.addEventListener('mousedown', function (event) {
    const mouseX = event.offsetX;
    const mouseY = event.offsetY;

    // Check if mouse is on resize handle of any rectangle
    for (let i = 0; i < rectangles.length; i++) {
        if (isMouseOnResizeHandle(mouseX, mouseY, rectangles[i])) {
            isResizing = true;
            selectedRectIndex = i;
            initialMouseX = mouseX;
            initialMouseY = mouseY;
            initialRect = { ...rectangles[i] }; // Copy the initial state of the rectangle
            return;
        }
    }

    for (let i = 0; i < texts.length; i++) {
        if (isMouseOnText(mouseX, mouseY, texts[i])) {
            isDragging = true;
            selectedTextIndex = i;
            return;
        }
    }

    // 사각형 드래그
    for (let i = 0; i < rectangles.length; i++) {
        if (isMouseOnRectangle(mouseX, mouseY, rectangles[i])) {
            isDragging2 = true;
            selectedRectIndex = i;
            initialMouseX = mouseX;
            initialMouseY = mouseY;
            initialRect = { ...rectangles[i] }; // Copy the initial state of the rectangle
            return;
        }
    }
});

// 캔버스에서 마우스 이동 이벤트 처리
canvas.addEventListener('mousemove', function (event) {
    const mouseX = event.offsetX;
    const mouseY = event.offsetY;

    if (isDragging2 && selectedRectIndex !== -1) {
        const dx = mouseX - initialMouseX;
        const dy = mouseY - initialMouseY;
        rectangles[selectedRectIndex].x = initialRect.x + dx;
        rectangles[selectedRectIndex].y = initialRect.y + dy;
        redrawCanvas();
    }

    if (isResizing && selectedRectIndex !== -1) {
        const dx = mouseX - initialMouseX;
        const dy = mouseY - initialMouseY;
        const rect = rectangles[selectedRectIndex];
        rect.width = initialRect.width + dx;
        rect.height = initialRect.height + dy;
        redrawCanvas();
    }

    if (isDragging && selectedTextIndex !== -1) {
        texts[selectedTextIndex].x = mouseX;
        texts[selectedTextIndex].y = mouseY;
        redrawCanvas();
    }
});

// 캔버스에서 마우스 버튼을 놓는 이벤트 처리
canvas.addEventListener('mouseup', function (event) {
    isDragging = false;
    selectedTextIndex = -1;
    selectedRectIndex = -1;
    isResizing = false;
    isDragging2 = false;
});

// 텍스트 추가 버튼에 이벤트 리스너 등록
addTextButton.addEventListener('click', addTextToCanvas);

function onReset() {
    ctx.save();
    ctx.fillStyle = 'white';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.restore();
    backColor.value = '#ffffff';
    cPushArray = [];
    cStep = -1;
    backgroundImage = null;
    texts = [];
    brushStrokes = [];
    penStrokes = [];
}

function onSave() {
    try {
        const url = canvas.toDataURL();
        const a = document.createElement('a');
        a.href = url;
        a.download = 'Art.png';
        a.click();
    } catch (error) {
        console.error('Error exporting canvas as image', error);
    }
}

function onKeyboard(event) {
    switch (event.keyCode) {
        case 81:
            onDraw();
            break;
        case 87:
            onBrush();
            break;
        case 69:
            onErase();
            break;
        case 65:
            onSquare();
            break;
        case 83:
            onTriangle();
            break;
        case 68:
            onCircle();
            break;
        case 90:
            if (event.ctrlKey) {
                onReturn();
            }
            break;
        case 46:
            onDelete();
            break;
    }
}

inputImage.addEventListener('change', onImg);
resetBtn.addEventListener('click', onReset);
save.addEventListener('click', onSave);
document.addEventListener('keydown', onKeyboard);

function onMouseMove(event) {
    if (isPainting && isErasing) {
        ctx.save();
        ctx.strokeStyle = backColor.value;
        ctx.lineWidth = lineWidth.value * 3;
        ctx.lineTo(event.offsetX, event.offsetY);
        ctx.stroke();
        ctx.restore();
    } else if (isPainting && isDrawing) {
        ctx.lineTo(event.offsetX, event.offsetY);
        ctx.stroke();

        if (penStrokes.length > 0 && penStrokes[penStrokes.length - 1].points) {
            penStrokes[penStrokes.length - 1].points.push({ x: event.offsetX, y: event.offsetY });
        }
    } else if (isPainting && isBrushing) {
        ctx.save();
        function distanceBetween(point1, point2) {
            return Math.sqrt(
                Math.pow(point2.x - point1.x, 2) + Math.pow(point2.y - point1.y, 2)
            );
        }
        function angleBetween(point1, point2) {
            return Math.atan2(point2.x - point1.x, point2.y - point1.y);
        }
        ctx.globalAlpha = '0.02';
        ctx.lineWidth = 0;
        ctx.globalCompositeOperation = 'source-over';
        var currentPoint = { x: event.offsetX, y: event.offsetY };
        var dist = distanceBetween(lastPoint, currentPoint);
        var angle = angleBetween(lastPoint, currentPoint);
        for (var i = 0; i < dist; i += 3) {
            x = event.offsetX + Math.sin(angle) * i - 25;
            y = event.offsetY + Math.cos(angle) * i - 25;
            ctx.beginPath();
            ctx.arc(x + 25, y + 25, lineWidth.value * 5, false, Math.PI * 2, false);
            ctx.closePath();
            ctx.fill();
            ctx.stroke();
            brushStrokes.push({
                x: x + 25,
                y: y + 25,
                radius: lineWidth.value * 5,
                color: ctx.fillStyle,
                alpha: ctx.globalAlpha
            });
        }
        lastPoint = currentPoint;
        ctx.restore();
    }
    ctx.moveTo(event.offsetX, event.offsetY);
}

function onMouseDown(event) {
    isPainting = true;
    lastPoint = { x: event.offsetX, y: event.offsetY };
    if (isDrawing || isErasing || issquare || isBrushing ) {
        cStep++;


        cPushArray.push(canvas.toDataURL());
        if (isDrawing) {
            penStrokes.push({ points: [{ x: event.offsetX, y: event.offsetY }], color: ctx.strokeStyle, width: ctx.lineWidth });
        }
        if (issquare) {
            const lineWidth = ctx.lineWidth * 20;
            const rect = {
                x: event.offsetX - lineWidth / 2,
                y: event.offsetY - lineWidth / 2,
                width: lineWidth,
                height: lineWidth,
                color: ctx.strokeStyle,
                lineWidth: ctx.lineWidth
            };
            rectangles.push(rect);
            redrawCanvas();
        }
    }

    ctx.save();
    ctx.beginPath();
    ctx.moveTo(event.offsetX, event.offsetY);
}

function onMouseUp() {
    isPainting = false;
    ctx.beginPath();
}


function onBrush() {
    isBrushing = !isBrushing;
    isErasing = false;
    isDrawing = false;
    issquare = false;
    brush.style.backgroundColor = isBrushing ? 'gray' : '#171717';
    eraser.style.backgroundColor = '#171717';
    drawing.style.backgroundColor = '#171717';
    square.style.backgroundColor = '#171717';
    redrawCanvas();
}

function onErase() {
    isErasing = !isErasing;
    isDrawing = false;
    isBrushing = false;
    eraser.style.backgroundColor = isErasing ? 'gray' : '#171717';
    brush.style.backgroundColor = '#171717';
    drawing.style.backgroundColor = '#171717';
    redrawCanvas();
}

function onDraw() {
    isDrawing = !isDrawing;
    isBrushing = false;
    isErasing = false;
    issquare = false;
    drawing.style.backgroundColor = isDrawing ? 'gray' : '#171717';
    brush.style.backgroundColor = '#171717';
    eraser.style.backgroundColor = '#171717';
    square.style.backgroundColor = '#171717';
    redrawCanvas();
}


function onColorChange(event) {
    ctx.strokeStyle = event.target.value;
    ctx.fillStyle = event.target.value;
}

function onBColorChange(event) {
    ctx.save();
    ctx.fillStyle = event.target.value;
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.restore();
}

function onDelete() {
    let tf = confirm('내용을 삭제하시겠습니까?');
    if (tf) {
        onReset();
    }
}

function onSquare() {
    issquare = true;
    isBrushing = false;
    isErasing = false;
    isDrawing = false;
    square.style.backgroundColor = 'gray';
    brush.style.backgroundColor = '#171717';
    eraser.style.backgroundColor = '#171717';
    drawing.style.backgroundColor = '#171717';

}


// 텍스트 더블 클릭 시 모달 열기
canvas.addEventListener('dblclick', function (event) {
    const mouseX = event.offsetX;
    const mouseY = event.offsetY;

    for (let i = 0; i < texts.length; i++) {
        if (isMouseOnText(mouseX, mouseY, texts[i])) {
            const padding = 15; // 클릭 범위를 확장하기 위한 여유 공간
            editingTextIndex = i;
            const textObj = texts[i];
            document.getElementById('popupTextInput').value = textObj.text;
            document.getElementById('popupTextColorPicker').value = textObj.color;
            document.getElementById('popupTextSizeInput').value = textObj.size;
            document.getElementById('popupFontWeightSelect').value = textObj.weight;
            document.getElementById('popupFontFamilySelect').value = textObj.font;
            document.getElementById('textEditPopup').style.display = 'block';
            return;
        }
    }
});

// 모달에서 업데이트 버튼 클릭 시 텍스트 수정
document.getElementById('updateTextButton').addEventListener('click', function (event) {
    if (editingTextIndex !== -1) {
        const textObj = texts[editingTextIndex];
        textObj.text = document.getElementById('popupTextInput').value;
        textObj.color = document.getElementById('popupTextColorPicker').value;
        textObj.size = document.getElementById('popupTextSizeInput').value;
        textObj.weight = document.getElementById('popupFontWeightSelect').value;
        textObj.font = document.getElementById('popupFontFamilySelect').value;
        redrawCanvas();
        document.getElementById('textEditPopup').style.display = 'none';
        editingTextIndex = -1;
    }
});

// 모달 닫기 버튼 클릭 시 모달 닫기
document.getElementById('closeTextPopup').addEventListener('click', function (event) {
    document.getElementById('textEditPopup').style.display = 'none';
    editingTextIndex = -1;
});

// 추가된 변수들
let editingTextIndex = -1;



function onWidthChange(event) {
    if (!isNaN(event.target.value)) {
        if (event.target.value <= 1400) {
            canvas.width = event.target.value;
            canvas.style.width = event.target.value + 'px';
        } else {
            alert('1400이하의 숫자만 입력 가능합니다!');
            event.target.value = 1000;
            canvas.style.width = event.target.value + 'px';
        }
    } else {
        alert('1400이하의 숫자만 입력 가능합니다!');
        event.target.value = 1000;
        canvas.style.width = event.target.value + 'px';
    }
    onReset();
    redrawCanvas();
    ctx.translate(0, canvas.height - canvas.clientHeight); // 추가된 라인
}

function onHeightChange(event) {
    if (!isNaN(event.target.value)) {
        if (event.target.value <= 900) {
            canvas.height = event.target.value;
            canvas.style.height = event.target.value + 'px';
        } else {
            alert('900이하의 숫자만 입력 가능합니다!');
            event.target.value = 900;
            canvas.style.height = event.target.value + 'px';
        }
    } else {
        alert('900이하의 숫자만 입력 가능합니다!');
        event.target.value = 900;
        canvas.style.height = event.target.value + 'px';
    }
    onReset();
    redrawCanvas();
    ctx.translate(0, canvas.height - canvas.clientHeight); // 추가된 라인
}





canvas.addEventListener('mousemove', onMouseMove);
canvas.addEventListener('mousedown', onMouseDown);
canvas.addEventListener('mouseup', onMouseUp);
canvas.addEventListener('mouseleave', onMouseUp);
brush.addEventListener('click', function () {
    onBrush();
    redrawCanvas();
});
eraser.addEventListener('click', function () {
    onErase();
    redrawCanvas();
});
drawing.addEventListener('click', function () {
    onDraw();
    redrawCanvas();
});

square.addEventListener('click', function () {
    onSquare();
    redrawCanvas();
});
color.addEventListener('change', onColorChange);
resetBtn.addEventListener('click', onDelete);
canvasWidth.addEventListener('change', onWidthChange);
canvasHeight.addEventListener('change', onHeightChange);

// JSON 저장 기능
function saveCanvasAsJSONV2() {
    const canvasState = {
        backgroundImage: backgroundImage ? backgroundImage.src : null,
        penStrokes: penStrokes,
        brushStrokes: brushStrokes,
        texts: texts
    };
    const json = JSON.stringify(canvasState);
    const a = document.createElement('a');
    const file = new Blob([json], { type: 'application/json' });
    a.href = URL.createObjectURL(file);
    a.download = 'canvasState.json';
    a.click();
}
function saveCanvasAsJSON() {
    const canvasState = {
        backgroundImage: backgroundImage ? backgroundImage.src : null,
        penStrokes: penStrokes,
        brushStrokes: brushStrokes,
        texts: texts
    };
    const json = JSON.stringify(canvasState);

    // 서버로 JSON 데이터 전송
    if (json) { // null 체크
        fetch('/ai-Json', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: json
        })
            .then(response => {
                // 서버 응답 처리
            })
            .catch(error => {
                // 오류 처리
            });
    }
}




// JSON 불러오기 기능

function loadCanvasFromJSON(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = function (e) {
        const json = e.target.result;
        const canvasState = JSON.parse(json);

        backgroundImage = null;
        penStrokes = canvasState.penStrokes || [];
        brushStrokes = canvasState.brushStrokes || [];
        texts = canvasState.texts || [];

        if (canvasState.backgroundImage) {
            const img = new Image();
            img.src = canvasState.backgroundImage;
            img.onload = function () {
                backgroundImage = img;
                redrawCanvas();
            };
        } else {
            redrawCanvas();
        }
    };
    reader.readAsText(file);
}

// "Load from JSON" 버튼 클릭 시 파일 선택 창이 열리도록 설정
document.getElementById('loadJson').addEventListener('click', function () {
    document.getElementById('loadJsonFile').click();
});

// 파일 선택 창에서 JSON 파일을 선택하면 호출되는 함수
document.getElementById('loadJsonFile').addEventListener('change', loadCanvasFromJSON);

// "Save as JSON" 버튼 클릭 시 캔버스 상태를 JSON 파일로 저장하는 함수 등록
document.getElementById('saveJson').addEventListener('click', saveCanvasAsJSON);



