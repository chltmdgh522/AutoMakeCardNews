import torch
from transformers import PreTrainedTokenizerFast, BartForConditionalGeneration, BartModel


tokenizer = PreTrainedTokenizerFast.from_pretrained('digit82/kobart-summarization')
model = BartForConditionalGeneration.from_pretrained('digit82/kobart-summarization')

text = """
 윤석열 대통령이 13일 황우여 국민의힘 비상대책위원장과 추경호 원내대표 등 국민의힘 신임 지도부와 상견례를 겸한 만찬을 함께했다. 윤 대통령이 여권 지도부와 만난 것은 4·10 총선에서 국민의힘이 참패한 이후 이날이 처음이다. 참석자들은 새 지도부 출범을 윤 대통령이 격려하는 자리였던 만큼 민감한 정치 현안보다는 당정 소통이 강조됐다고 전했다. 정치권에서는 야당의 특검법 공세를 앞두고 윤 대통령이 국민의힘 지도부를 만나 여권 내부를 추스르는 자리였다는 해석이 나왔다. 이날 서울 용산 대통령실에서 2시간가량 진행된 만찬에는 윤 대통령 외에 대통령실에서 정진석 비서실장과 홍철호 정무수석 등이 배석했다. 국민의힘에서는 황 위원장을 비롯해 전날 지명된 유상범·전주혜·엄태영·김용태 비대위원과 당연직 비대위원인 추 원내대표, 정점식 정책위의장 등 비대위원 7명이 참석했다. 새로 임명된 성일종 사무총장과 배준영 원내수석부대표, 조은희 비대위원장 비서실장도 배석했다. 대통령실 김수경 대변인은 “국민의힘 비대위가 공식 출범하자마자 곧바로 대통령 초청으로 만찬을 개최한 것은 국정 현안, 특히 민생 현안이 산적해 있고, 이 문제를 풀기 위해서는 여당의 적극적 역할이 중요하다는 데 당정이 공감했기 때문”이라며 “대통령은 만찬 내내 당 지도부의 의견을 경청했으며 총선에서 나타난 민심을 잘 새겨서 국정 운영에 적극 반영하겠다는 뜻을 전했다”고 밝혔다. 이에 황 위원장 역시 “전당대회 준비 등 당 현안을 차질 없이 챙기는 한편 원활한 국정 운영을 위해 당정 협력을 강화하겠다”고 말했다고 김 대변인은 전했다. 참석자들은 윤 대통령이 총선 이후 당이 어려운 시기에 중책을 맡은 국민의힘 새 지도부에 감사를 표하는 자리였던 만큼 선거 기간 노고를 격려하고 향후 당정 관계의 소통을 강조했다고 전했다. 추 원내대표는 본지에 “오늘은 대통령실과 당 지도부의 첫 상견례 자리였던 만큼 원내 현안이나 대화 의제를 따로 준비하지 않았다”며 “앞으로도 수시로 대통령실과 소통할 기회가 많지 않겠느냐”고 했다. 정점식 의장도 “서로 인사를 나누는 편한 자리였기 때문에 정치 현안 이야기를 나눌 분위기는 아니다”라고 했다.
"""
text = text.replace('\n', ' ')

raw_input_ids = tokenizer.encode(text)
input_ids = [tokenizer.bos_token_id] + raw_input_ids + [tokenizer.eos_token_id]

summary_ids = model.generate(torch.tensor([input_ids]),  num_beams=4,  max_length=512,  eos_token_id=1)
decode = tokenizer.decode(summary_ids.squeeze().tolist(), skip_special_tokens=True)
print(decode)
