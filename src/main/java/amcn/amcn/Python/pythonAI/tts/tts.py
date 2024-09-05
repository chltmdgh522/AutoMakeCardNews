# # -*- coding: utf-8 -*-
#
# # import speech_recognition as sr
#
# from gtts import gTTS
#
# import os
#
# import time
#
# import playsound
#
# import sys
# import pyttsx3
# """
# def speak(text):
#     tts = gTTS(text=text, lang='ko')
#     tts.save("output.mp3")
#
# if __name__ == "__main__":
#     if len(sys.argv) > 1:
#         content = sys.argv[1]
#         speak(content)
#     else:
#         print("No content provided")
#         """
#
# # def speak(text):
# #
# #      tts = gTTS(text=text, lang='ko')
# #
# #      filename='voice2.mp3'
# #
# #      tts.save(filename)
# #      print(text)
# #      playsound.playsound(filename)
# #
# def speak(content):
#     engine = pyttsx3.init()
#     engine.setProperty('rate', 230)
#     engine.say(content)
#     engine.runAndWait()
#
# if __name__ == "__main__":
#     if len(sys.argv) > 1:
#         content = sys.argv[1]
#         speak(content)
#     else:
#         print("No content provided")
#



from gtts import gTTS
import sys

def generate_tts(text, output_file):
    tts = gTTS(text, lang='ko')
    print("나느 파이썬 tts")
    tts.save(output_file)

if __name__ == '__main__':
    text = sys.argv[1]  # 첫 번째 인자로 텍스트 받기
    output_file = sys.argv[2]  # 두 번째 인자로 파일명 받기
    generate_tts(text, output_file)
