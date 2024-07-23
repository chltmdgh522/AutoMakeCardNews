from transformers import PreTrainedTokenizerFast, BartForConditionalGeneration, BartModel
from model import KoBARTConditionalGeneration

# 저장된 모델 파일 경로
model_path = "C:\\Users\\chltm\\PycharmProjects\\amcn_AI\\summary\\model\\checkpoint\\last-v2.ckpt"

# 모델 및 토크나이저 로드
tokenizer = PreTrainedTokenizerFast.from_pretrained('digit82/kobart-summarization')
loaded_model = KoBARTConditionalGeneration.load_from_checkpoint(model_path)

# 텍스트 요약 생성
input_text = """
1일 오후 9시까지 최소 20만3220명이 코로나19에 신규 확진됐다. 또다시 동시간대 최다 기록으로, 사상 처음 20만명대에 진입했다.
방역 당국과 서울시 등 각 지방자치단체에 따르면 이날 0시부터 오후 9시까지 전국 신규 확진자는 총 20만3220명으로 집계됐다.
국내 신규 확진자 수가 20만명대를 넘어선 것은 이번이 처음이다.
                                            .
                                            .
                                            . 
                                          (중략)
"""
input_text=input_text[:2000]
summary_text = loaded_model.generate(input_text, tokenizer, num_beams=4, max_length=512, early_stopping=True)
print(summary_text)
