import sys
from gtts import gTTS

text = sys.argv[1]
output_path = "/home/ubuntu/AutoMakeCardNews/video_tts.mp3"

# TTS 생성
tts = gTTS(text=text, lang='ko')
tts.save(output_path)
