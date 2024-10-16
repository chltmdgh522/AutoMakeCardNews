const canvas = document.getElementById('imageCanvas');
const ctx = canvas.getContext('2d');
const lineWidth = document.getElementById('line_width');
const color = document.getElementById('text_color');
const backColor = document.getElementById('background_color');
const resetBtn = document.getElementById('reset');
const inputImage = document.getElementById('file');
const save = document.getElementById('save');
const drawing = document.getElementById('drawing');

const canvasWidth = document.getElementById('canvas_width');
const canvasHeight = document.getElementById('canvas_height');
const square = document.getElementById('square');
const fill_square = document.getElementById('fill_square');

const fillSquareButton = document.getElementById('filter');
const filterPopup = document.getElementById('filterPopup5');
const overlay = document.getElementById('overlay5');
const closePopupButton = document.getElementById('closePopup5');
const applyFilterButton = document.getElementById('applyFilter5');
const filterForm = document.getElementById('filterForm5');
canvas.width = canvasWidth.value;
canvas.height = canvasHeight.value;
ctx.lineWidth = lineWidth.value;

let cPushArray = [];
let cStep = -1;
// 경계선 색 square
let rectangles = [];

let rectfillangles = [];


let isPainting = false;
let isDrawing = false;
let isBrushing = false;
let isErasing = false;
let issquare = false;
let isfill_square = false;
let backgroundColor = '#FFFFFF'; // 기본 배경색을 검정색으로 설정

const textInput = document.getElementById('textInput');  // Changed from input to textarea

const textColorPicker = document.getElementById('textColorPicker');
const textSizeInput = document.getElementById('textSizeInput');
const fontWeightSelect = document.getElementById('fontWeightSelect');
const fontFamilySelect = document.getElementById('fontFamilySelect');
const addTextButton = document.getElementById('addTextButton');


// 팝업창 요소 선택
const colorPopup = document.getElementById('colorPopup');
const closeColorPopup = document.getElementById('closeColorPopup');
const rectColorPicker = document.getElementById('rectColorPicker');
const updateColorButton = document.getElementById('updateColorButton');

const colorPopup2 = document.getElementById('colorPopup2');
const closeColorPopup2 = document.getElementById('closeColorPopup2');
const rectColorPicker2 = document.getElementById('rectColorPicker2');
const updateColorButton2 = document.getElementById('updateColorButton2');

const colorPopup3 = document.getElementById('colorPopup3');
const closeColorPopup3 = document.getElementById('closeColorPopup3');
const rectColorPicker3 = document.getElementById('rectColorPicker3');
const updateColorButton3 = document.getElementById('updateColorButton3');


let editingRectIndex = -1;
let editingRectIndex2 = -1;
let editingRectIndex3 = -1;


let texts = [];
let isDragging = false;
let isDragging2 = false;
let isDragging3 = false;
let isDragging4 = false;
let isDragging5 = false;

let selectedTextIndex = -1;
let selectedRectIndex = -1;
let selectedfillRectIndex = -1;
let selectedpen = -1;
let selectedbrush = -1;

let currentFilter = 'none';

let resizeHandleSize = 10;
let resizeHandleSize2 = 10;
let backgroundImage = null;

let brushStrokes = [];
let penStrokes = [];


fillSquareButton.addEventListener('click', () => {
    filterPopup.style.display = 'block';
    overlay.style.display = 'block';
    isDrawing = false;
    isfill_square = false;
    issquare = false;
    drawing.style.backgroundColor = '#171717';
    square.style.backgroundColor = '#171717';
    fill_square.style.backgroundColor = '#171717';
});

closePopupButton.addEventListener('click', () => {
    filterPopup.style.display = 'none';
    overlay.style.display = 'none';
});

overlay.addEventListener('click', () => {
    filterPopup.style.display = 'none';
    overlay.style.display = 'none';
});


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
        x: 12,
        y: 660
    });
    redrawCanvas();
}

function onBColorChange(event) {
    backgroundColor = event.target.value;
    redrawCanvas();
}


function redrawCanvas(filter = currentFilter) {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.setTransform(1, 0, 0, 1, 0, 0);

    let finalcolor = backgroundColor;

    // 배경색 설정
    ctx.save();
    ctx.fillStyle = finalcolor;
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.restore();

    if (backgroundImage) {
        ctx.filter = filter; // Set the filter before drawing the image
        ctx.drawImage(backgroundImage, 0, 0, canvas.width, canvas.height);
        ctx.filter = 'none'; // Reset filter after drawing the image
    }

    // Draw pen strokes
    penStrokes.forEach((stroke, index) => {
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

        // Draw highlight for the selected pen stroke
        if (index === selectedpen) {
            ctx.save();
            ctx.strokeStyle = 'rgba(255, 0, 0, 0.5)';
            ctx.lineWidth = stroke.width + 5; // Make the highlight slightly thicker
            ctx.beginPath();
            ctx.moveTo(stroke.points[0].x, stroke.points[0].y);
            for (let i = 1; i < stroke.points.length; i++) {
                ctx.lineTo(stroke.points[i].x, stroke.points[i].y);
            }
            ctx.stroke();
            ctx.restore();
        }
    });

    // Draw brush strokes
    brushStrokes.forEach(stroke => {
        ctx.save();
        ctx.globalAlpha = stroke.alpha;
        ctx.fillStyle = stroke.color;
        ctx.beginPath();
        ctx.arc(stroke.x, stroke.y, stroke.radius, 0, Math.PI * 2);
        ctx.fill();
        ctx.restore();
    });

    // Draw filled rectangles
    rectfillangles.forEach((rect, index) => {
        ctx.save();
        ctx.beginPath();
        ctx.fillStyle = rect.color;
        ctx.fillRect(rect.x, rect.y, rect.width, rect.height);

        ctx.strokeStyle = rect.color;
        ctx.lineWidth = rect.lineWidth;
        ctx.strokeRect(rect.x, rect.y, rect.width, rect.height);
        ctx.stroke();
        ctx.restore();

        // Draw resize handle for the selected rectangle only
        if (index === selectedfillRectIndex) {
            ctx.save();
            ctx.fillStyle = 'red';
            ctx.fillRect(rect.x + rect.width - resizeHandleSize2 / 2, rect.y + rect.height - resizeHandleSize2 / 2, resizeHandleSize2, resizeHandleSize2);
            ctx.restore();
        }
    });

    // Draw rectangles
    rectangles.forEach((rect, index) => {
        ctx.save();
        ctx.beginPath();
        ctx.strokeStyle = rect.color;
        ctx.lineWidth = rect.lineWidth;
        ctx.rect(rect.x, rect.y, rect.width, rect.height);
        ctx.stroke();
        ctx.restore();

        // Draw resize handle for the selected rectangle only
        if (index === selectedRectIndex) {
            ctx.save();
            ctx.fillStyle = 'red';
            ctx.fillRect(rect.x + rect.width - resizeHandleSize / 2, rect.y + rect.height - resizeHandleSize / 2, resizeHandleSize, resizeHandleSize);
            ctx.restore();
        }
    });

    // Draw texts (this should be after shapes and lines)
    texts.forEach(textObj => {
        const lines = textObj.text.split('\n');
        ctx.fillStyle = textObj.color;
        ctx.font = `${textObj.weight} ${textObj.size}px ${textObj.font}`;
        const lineHeight = textObj.size * 1.2;
        lines.forEach((line, index) => {
            ctx.fillText(line, textObj.x, textObj.y + (index * lineHeight));
        });
    });
}

// Apply selected filter
applyFilterButton.addEventListener('click', () => {
    const selectedFilter = filterForm.querySelector('input[name="filter5"]:checked');
    if (selectedFilter) {
        applyFilter(selectedFilter.value);
    } else {
        alert('필터를 선택하세요.');
    }
});

let filterLastValue;

// Function to apply the selected filter
function applyFilter(filter) {
    currentFilter = filter; // 현재 필터 상태 업데이트
    let filterValue;

    switch (filter) {
        case 'blur':
            filterValue = 'blur(5px)';
            break;
        case 'brighten':
            filterValue = 'brightness(150%)';
            break;
        case 'contrast':
            filterValue = 'contrast(150%)';
            break;
        case 'invert': // 반전 효과
            filterValue = 'invert(100%)';
            break;
        case 'sepia':
            filterValue = 'sepia(100%)';
            break;
        default:
            filterValue = 'none';
    }

    filterLastValue = filterValue;
    redrawCanvas(filterValue); // Pass the filter to redrawCanvas
}


function isMouseOnRectangle(mouseX, mouseY, rect) {

    const padding = 1; // 클릭 범위를 확장하기 위한 여유 공간
    return mouseX >= rect.x - padding && mouseX <= rect.x + rect.width + padding &&
        mouseY >= rect.y - padding && mouseY <= rect.y + rect.height + padding;
}

function isMouseOnResizeHandle(mouseX, mouseY, rect) {
    return mouseX >= rect.x + rect.width - resizeHandleSize && mouseX <= rect.x + rect.width + resizeHandleSize &&
        mouseY >= rect.y + rect.height - resizeHandleSize && mouseY <= rect.y + rect.height + resizeHandleSize;
}

function isMouseOnRectangle2(mouseX, mouseY, rect) {

    const padding2 = 1; // 클릭 범위를 확장하기 위한 여유 공간
    return mouseX >= rect.x - padding2 && mouseX <= rect.x + rect.width + padding2 && mouseY >= rect.y - padding2 && mouseY <= rect.y + rect.height + padding2;
}

function isMouseOnResizeHandle2(mouseX, mouseY, rect) {
    return mouseX >= rect.x + rect.width - resizeHandleSize2 && mouseX <= rect.x + rect.width + resizeHandleSize2 &&
        mouseY >= rect.y + rect.height - resizeHandleSize2 && mouseY <= rect.y + rect.height + resizeHandleSize2;
}


function isMouseOnPen(mouseX, mouseY, stroke) {
    const padding = 5; // 클릭 범위를 확장하기 위한 여유 공간
    for (let i = 0; i < stroke.points.length - 1; i++) {
        const p1 = stroke.points[i];
        const p2 = stroke.points[i + 1];
        if (distanceToSegment(mouseX, mouseY, p1, p2) <= padding) {
            return true;
        }
    }
    return false;
}

function isMouseOnBrush(mouseX, mouseY, stroke) {
    console.log("dddd");
    const dx = mouseX - stroke.x;
    const dy = mouseY - stroke.y;
    const distance = Math.sqrt(dx * dx + dy * dy);
    return distance <= stroke.radius + 5; // 클릭 범위를 확장하기 위한 여유 공간
}

function distanceToSegment(px, py, p1, p2) {
    const x = px;
    const y = py;
    const x1 = p1.x;
    const y1 = p1.y;
    const x2 = p2.x;
    const y2 = p2.y;

    const A = x - x1;
    const B = y - y1;
    const C = x2 - x1;
    const D = y2 - y1;

    const dot = A * C + B * D;
    const len_sq = C * C + D * D;
    let param = -1;
    if (len_sq !== 0) { // in case of 0 length line
        param = dot / len_sq;
    }

    let xx, yy;

    if (param < 0) {
        xx = x1;
        yy = y1;
    } else if (param > 1) {
        xx = x2;
        yy = y2;
    } else {
        xx = x1 + param * C;
        yy = y1 + param * D;
    }

    const dx = x - xx;
    const dy = y - yy;
    return Math.sqrt(dx * dx + dy * dy);
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
            initialRect = {...rectangles[i]}; // Copy the initial state of the rectangle
            redrawCanvas();
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
            initialRect = {...rectangles[i]}; // Copy the initial state of the rectangle
            redrawCanvas();
            return;
        }
    }

    // Check if mouse is on resize handle of any rectangle
    for (let i = 0; i < rectfillangles.length; i++) {
        if (isMouseOnResizeHandle2(mouseX, mouseY, rectfillangles[i])) {
            isResizing2 = true;
            selectedfillRectIndex = i;
            initialMouseX = mouseX;
            initialMouseY = mouseY;
            initialRect = {...rectfillangles[i]}; // Copy the initial state of the rectangle
            redrawCanvas();
            return;
        }
    }

    // 사각형 드래그
    for (let i = 0; i < rectfillangles.length; i++) {
        if (isMouseOnRectangle2(mouseX, mouseY, rectfillangles[i])) {
            isDragging3 = true;
            selectedfillRectIndex = i;
            initialMouseX = mouseX;
            initialMouseY = mouseY;
            initialRect = {...rectfillangles[i]}; // Copy the initial state of the rectangle
            redrawCanvas();
            return;
        }
    }

    // 펜 드래그
    for (let i = 0; i < penStrokes.length; i++) {
        if (isMouseOnPen(mouseX, mouseY, penStrokes[i])) {

            isDragging4 = true;
            selectedpen = i;
            initialMouseX = mouseX;
            initialMouseY = mouseY;
            initialStroke = {...penStrokes[i]}; // Copy the initial state of the pen stroke
            redrawCanvas();
            return;
        }
    }

    // 브러쉬 드래그
    for (let i = 0; i < brushStrokes.length; i++) {
        if (isMouseOnBrush(mouseX, mouseY, brushStrokes[i])) {
            isDragging5 = true;
            selectedbrush = i;
            initialMouseX = mouseX;
            initialMouseY = mouseY;
            initialStroke = {...brushStrokes[i]}; // Copy the initial state of the pen stroke
            redrawCanvas();
            return;
        }
    }

    selectedbrush = -1;
    selectedRectIndex = -1;
    selectedpen = -1;
    selectedfillRectIndex = -1;
    redrawCanvas();
});

// 캔버스에서 마우스 이동 이벤트 처리
canvas.addEventListener('mousemove', function (event) {
    const mouseX = event.offsetX;
    const mouseY = event.offsetY;

    if (isDragging2 && selectedRectIndex !== -1) {
        console.log(selectedRectIndex)
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


    if (isDragging3 && selectedfillRectIndex !== -1) {

        const dx = mouseX - initialMouseX;
        const dy = mouseY - initialMouseY;
        rectfillangles[selectedfillRectIndex].x = initialRect.x + dx;
        rectfillangles[selectedfillRectIndex].y = initialRect.y + dy;
        redrawCanvas();
    }

    if (isResizing2 && selectedfillRectIndex !== -1) {

        const dx = mouseX - initialMouseX;
        const dy = mouseY - initialMouseY;
        const rect = rectfillangles[selectedfillRectIndex];
        rect.width = initialRect.width + dx;
        rect.height = initialRect.height + dy;
        redrawCanvas();
    }


    if (isDragging4 && selectedpen !== -1) {

        const mouseX = event.offsetX;
        const mouseY = event.offsetY;
        const dx = mouseX - initialMouseX;
        const dy = mouseY - initialMouseY;

        penStrokes[selectedpen].points.forEach(point => {
            point.x += dx;
            point.y += dy;
        });

        initialMouseX = mouseX;
        initialMouseY = mouseY;

        redrawCanvas();
    }
    if (isDragging5 && selectedbrush !== -1) {
        const mouseX = event.offsetX;
        const mouseY = event.offsetY;
        const dx = mouseX - initialMouseX;
        const dy = mouseY - initialMouseY;

        brushStrokes[selectedbrush].x += dx;
        brushStrokes[selectedbrush].y += dy;

        initialMouseX = mouseX;
        initialMouseY = mouseY;

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

    isResizing = false;
    isDragging2 = false;

    //   selectedfillRectIndex = -1;
    isResizing2 = false;
    isDragging3 = false;

    isDragging4 = false;
    isDragging5 = false;

});

// 텍스트 추가 버튼에 이벤트 리스너 등록
addTextButton.addEventListener('click', addTextToCanvas);

function onReset() {
    ctx.save();
    ctx.fillStyle = 'white';
    backgroundColor = '#FFFFFF';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    ctx.restore();
    backColor.value = '#ffffff';
    cPushArray = [];
    cStep = -1;
    backgroundImage = null;
    texts = [];
    brushStrokes = [];
    penStrokes = [];
    rectangles = [];
    rectfillangles = [];
}

// form 제출 시 이벤트 처리
document.getElementById('popup-form5').addEventListener('submit', async function(event) {
    event.preventDefault();

    // 선택된 체크박스 값 가져오기
    const selectedOptions = document.querySelectorAll('input[name="options5"]:checked');
    const selectedValues = Array.from(selectedOptions).map(option => option.value);

    // 선택한 옵션이 없을 경우 경고
    if (selectedValues.length === 0) {
        alert('옵션을 하나 이상 선택해야 합니다.');
        return;
    }

    // 카드뉴스 이미지 다운로드 처리
    if (selectedValues.includes('image')) {
        onSave();
    }

    // TTS 전송 처리
    if (selectedValues.includes('audio')) {
        await sendTTS();
    }

    // 동영상 전송 처리
    if (selectedValues.includes('video')) {
        await sendVideo();
    }
});

// 이미지 다운로드 함수
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

// TTS 요청을 서버로 보내는 함수 (비동기 처리)
async function sendTTS() {
    if (texts.length > 0) {
        const allTexts = texts.map(item => item.text).join(' '); // 모든 텍스트 연결

        try {
            const response = await fetch('/tts', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ text: allTexts })
            });

            if (response.ok) {
                // MP3 파일이 blob 형식으로 반환되므로 이를 처리
                const blob = await response.blob();

                // 다운로드 링크를 만들어 클릭 이벤트 트리거
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'tts_audio.mp3'; // 파일명을 지정
                a.click();

                // URL 해제
                window.URL.revokeObjectURL(url);
                console.log('TTS 파일 다운로드 완료');
            } else {
                console.error('TTS 요청 실패:', response.status);
            }
        } catch (error) {
            console.error('TTS 요청 중 오류 발생:', error);
        }
    } else {
        alert('추가된 텍스트가 없습니다.');
    }
}


async function sendVideo() {
    if (texts.length === 0) {
        alert('추가된 텍스트가 없습니다.');
        return;
    }

    // canvas 이미지가 없는 경우 처리
    const imageData = canvas.toDataURL();
    if (!imageData) {
        alert('이미지가 없습니다.');
        return;
    }

    alert("5초 정도 기다려주세요");
    if (texts.length > 0) {
        const allTexts = texts.map(item => item.text).join(' ');

        // canvas 이미지를 base64로 변환
        const imageData = canvas.toDataURL();

        try {
            // TTS 파일 생성 후 비디오 요청
            const ttsResponse = await fetch('/tts2', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ text: allTexts })
            });

            if (ttsResponse.ok) {
                const audioBlob = await ttsResponse.blob();

                // 폼 데이터에 이미지와 TTS 파일 추가
                const formData = new FormData();
                formData.append('imageData', imageData); // 이미지 데이터 추가
                formData.append('audioFile', audioBlob, 'video_tts.mp3'); // TTS 파일 추가

                const videoResponse = await fetch('/video', {
                    method: 'POST',
                    body: formData
                });

                if (videoResponse.ok) {
                    const videoBlob = await videoResponse.blob();

                    // 비디오 파일 다운로드
                    const url = window.URL.createObjectURL(videoBlob);
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = 'video.mp4';
                    a.click();

                    window.URL.revokeObjectURL(url);
                    console.log('동영상 파일 다운로드 완료');
                } else {
                    console.error('비디오 생성 실패:', videoResponse.status);
                }
            } else {
                console.error('TTS 요청 실패:', ttsResponse.status);
            }
        } catch (error) {
            console.error('비디오 요청 중 오류 발생:', error);
        }
    } else {
        alert('추가된 텍스트가 없습니다.');
    }
}



backColor.addEventListener('change', onBColorChange);

function onKeyboard(event) {
    switch (event.keyCode) {

        case 90:
            if (event.ctrlKey) {
                onReturn();
            }
            break;

        case 46:
            onDelete();
            break;
        case 8: // 'b' 키 (사각형 삭제)
            deleteSelectedRectangle();
            break;
    }
}

inputImage.addEventListener('change', onImg);
resetBtn.addEventListener('click', onDelete);
//save.addEventListener('click', onSave);
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
            penStrokes[penStrokes.length - 1].points.push({x: event.offsetX, y: event.offsetY});
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
        var currentPoint = {x: event.offsetX, y: event.offsetY};
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
    lastPoint = {x: event.offsetX, y: event.offsetY};
    if (isDrawing || isErasing || issquare || isBrushing || isfill_square) {
        cStep++;


        cPushArray.push(canvas.toDataURL());
        if (isDrawing) {
            penStrokes.push({
                points: [{x: event.offsetX, y: event.offsetY}],
                color: ctx.strokeStyle,
                width: ctx.lineWidth
            });
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
        if (isfill_square) {
            const lineWidth = ctx.lineWidth * 20;
            const rect = {
                x: event.offsetX - lineWidth / 2,
                y: event.offsetY - lineWidth / 2,
                width: lineWidth,
                height: lineWidth,
                color: ctx.strokeStyle,
                lineWidth: ctx.lineWidth
            };
            rectfillangles.push(rect);
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


function onDraw() {
    isDrawing = !isDrawing;
    isfill_square = false;
    issquare = false;
    drawing.style.backgroundColor = isDrawing ? 'gray' : '#171717';
    square.style.backgroundColor = '#171717';
    fill_square.style.backgroundColor = '#171717';


    redrawCanvas();
}

// 사각형 삭제 함수
function deleteSelectedRectangle() {
    console.log("deleteSelectedRectangle called");
    console.log("selectedRectIndex:", selectedRectIndex);
    if (selectedRectIndex !== -1) {
        rectangles.splice(selectedRectIndex, 1); // 선택된 사각형 삭제
        selectedRectIndex = -1; // 선택 상태 초기화
        redrawCanvas(); // 캔버스 다시 그리기
    } else if (selectedfillRectIndex !== -1) {
        rectfillangles.splice(selectedfillRectIndex, 1); // 선택된 사각형 삭제
        selectedfillRectIndex = -1; // 선택 상태 초기화
        redrawCanvas(); // 캔버스 다시 그리기
    } else if (selectedpen !== -1) {
        penStrokes.splice(selectedpen, 1); // 선택된 사각형 삭제
        selectedpen = -1; // 선택 상태 초기화
        redrawCanvas(); // 캔버스 다시 그리기
    }
}

function onColorChange(event) {
    ctx.strokeStyle = event.target.value;
    ctx.fillStyle = event.target.value;
}


function onDelete() {
    isDrawing = false;
    isfill_square = false;
    issquare = false;
    drawing.style.backgroundColor = '#171717';
    square.style.backgroundColor = '#171717';
    fill_square.style.backgroundColor = '#171717';

    let tf = confirm('내용을 삭제하시겠습니까?');
    console.log('Confirm returned:', tf); // tf 값 출력
    if (tf) {
        onReset();
    } else {

    }
}

function onSquare() {
    issquare = !issquare;
    isfill_square = false;
    isDrawing = false;
    square.style.backgroundColor = issquare ? 'gray' : '#171717';
    drawing.style.backgroundColor = '#171717';
    fill_square.style.backgroundColor = '#171717';


}


function onFill_square() {
    isfill_square = !isfill_square;
    issquare = false;
    isDrawing = false;

    fill_square.style.backgroundColor = isfill_square ? 'gray' : '#171717';
    square.style.backgroundColor = '#171717';
    drawing.style.backgroundColor = '#171717';

}

// 사각형 더블 클릭 시 팝업 열기
canvas.addEventListener('dblclick', function (event) {
    const mouseX = event.offsetX;
    const mouseY = event.offsetY;

    for (let i = 0; i < rectangles.length; i++) {
        if (isMouseOnRectangle(mouseX, mouseY, rectangles[i])) {
            editingRectIndex = i;
            const rect = rectangles[i];
            rectColorPicker.value = rect.color;
            colorPopup.style.display = 'block';
            return;
        }
    }
});

// 팝업 닫기
closeColorPopup.addEventListener('click', function () {
    colorPopup.style.display = 'none';
    editingRectIndex = -1;
});

// 색깔 변경 버튼 클릭 시
updateColorButton.addEventListener('click', function () {
    if (editingRectIndex !== -1) {
        const rect = rectangles[editingRectIndex];
        rect.color = rectColorPicker.value;
        redrawCanvas();
        colorPopup.style.display = 'none';
        editingRectIndex = -1;
    }
});


// 색채운사각형 더블 클릭 시 팝업 열기
canvas.addEventListener('dblclick', function (event) {
    const mouseX = event.offsetX;
    const mouseY = event.offsetY;

    for (let i = 0; i < rectfillangles.length; i++) {
        if (isMouseOnRectangle2(mouseX, mouseY, rectfillangles[i])) {
            editingRectIndex2 = i;
            const rect = rectfillangles[i];
            rectColorPicker2.value = rect.color;
            colorPopup2.style.display = 'block';
            return;
        }
    }
});

// 팝업 닫기
closeColorPopup2.addEventListener('click', function () {
    colorPopup2.style.display = 'none';
    editingRectIndex2 = -1;
});

// 색깔 변경 버튼 클릭 시
updateColorButton2.addEventListener('click', function () {
    if (editingRectIndex2 !== -1) {
        console.log(editingRectIndex2)
        const rect = rectfillangles[editingRectIndex2];
        rect.color = rectColorPicker2.value;
        redrawCanvas();
        colorPopup2.style.display = 'none';
        editingRectIndex2 = -1;
    }
});


// 펜으로 그린거 더블 클릭 시 팝업 열기
canvas.addEventListener('dblclick', function (event) {
    const mouseX = event.offsetX;
    const mouseY = event.offsetY;

    for (let i = 0; i < penStrokes.length; i++) {
        if (isMouseOnPen(mouseX, mouseY, penStrokes[i])) {
            editingRectIndex3 = i;
            const rect = penStrokes[i];
            rectColorPicker3.value = rect.color;
            colorPopup3.style.display = 'block';
            return;
        }
    }
});

// 팝업 닫기
closeColorPopup3.addEventListener('click', function () {
    colorPopup3.style.display = 'none';
    editingRectIndex3 = -1;
});

// 색깔 변경 버튼 클릭 시
updateColorButton3.addEventListener('click', function () {
    if (editingRectIndex3 !== -1) {
        console.log(editingRectIndex3)
        const rect = penStrokes[editingRectIndex3];
        rect.color = rectColorPicker3.value;
        redrawCanvas();
        colorPopup3.style.display = 'none';
        editingRectIndex3 = -1;
    }
});


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

// 확성기 버튼 클릭 시 모든 텍스트를 읽어주는 기능
document.getElementById('speakerButton').addEventListener('click', function () {
    if (texts.length > 0) {
        const allTexts = texts.map(item => item.text).join(' '); // 모든 텍스트를 공백으로 구분하여 연결
        speakText(allTexts); // 모든 텍스트 읽기
    } else {
        alert('추가된 텍스트가 없습니다.');
    }
});

// 텍스트를 읽어주는 함수
function speakText(text) {
    const utterance = new SpeechSynthesisUtterance(text);
    window.speechSynthesis.speak(utterance);
}

// 페이지 이동 또는 새로고침 시 TTS 종료
window.addEventListener('beforeunload', function () {
    window.speechSynthesis.cancel(); // TTS 종료
});

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

drawing.addEventListener('click', function () {
    onDraw();
    redrawCanvas();
});

square.addEventListener('click', function () {
    onSquare();
    console.log('gdd');
    redrawCanvas();
});


fill_square.addEventListener('click', function () {
    onFill_square();
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
    const file = new Blob([json], {type: 'application/json'});
    a.href = URL.createObjectURL(file);
    a.download = 'canvasState.json';
    a.click();
}

function saveCanvasAsJSON() {
    const canvasState = {
        backgroundImage: backgroundImage ? backgroundImage.src : null,
        penStrokes: penStrokes,
        brushStrokes: brushStrokes,
        texts: texts,
        rectangles: rectangles,
        rectfillangles: rectfillangles,
        backgroundColor: backgroundColor,
        currentFilter: filterLastValue,


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