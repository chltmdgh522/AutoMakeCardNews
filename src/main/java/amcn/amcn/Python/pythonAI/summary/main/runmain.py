import sys
sys.path.append('/home/ununtu/AutoMakeCardNews/src/main/java/amcn/amcn/Python/pythonAI/venv/Lib/site-packages')

from transformers import PreTrainedTokenizerFast, BartForConditionalGeneration
import torch

def summary(content):
    try:
        tokenizer = PreTrainedTokenizerFast.from_pretrained('digit82/kobart-summarization')
        model = BartForConditionalGeneration.from_pretrained('digit82/kobart-summarization')

        text = content.replace('\n', ' ')
        print('파이썬 요약')
        raw_input_ids = tokenizer.encode(text, truncation=True)
        input_ids = [tokenizer.bos_token_id] + raw_input_ids + [tokenizer.eos_token_id]

        summary_ids = model.generate(torch.tensor([input_ids]), num_beams=4, max_length=512, eos_token_id=1)
        decode = tokenizer.decode(summary_ids.squeeze().tolist(), skip_special_tokens=True)
        print('파이썬 요약2')
        return decode
    except Exception as e:
        print(f"Error: {e}", file=sys.stderr)
        sys.exit(1)

if __name__ == "__main__":
    if len(sys.argv) > 1:
        content = sys.argv[1]
        print('111 요약')
        print(summary(content))
    else:
        print("No content provided", file=sys.stderr)
        sys.exit(1)