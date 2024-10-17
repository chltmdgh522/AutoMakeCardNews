from moviepy.editor import *

# 이미지와 오디오 파일 경로 설정
image_path ="/home/ubuntu/AutoMakeCardNews/video_image.png"
audio_path = "/home/ubuntu/AutoMakeCardNews/video_tts.mp3"
output_video_path = "/home/ubuntu/AutoMakeCardNews/video.mp4"

# 이미지 클립 생성 (오디오 파일 길이만큼 유지)
image_clip = ImageClip(image_path).set_duration(AudioFileClip(audio_path).duration)

# 오디오 클립 로드
audio_clip = AudioFileClip(audio_path)

# 이미지와 오디오 결합
video_clip = image_clip.set_audio(audio_clip)
# 동영상 파일로 저장
video_clip.write_videofile(output_video_path, fps=24)
