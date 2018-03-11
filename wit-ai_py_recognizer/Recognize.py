import requests
import json
from Recorder import record_audio, read_audio
import sys

# Wit speech API endpoint
API_ENDPOINT = 'https://api.wit.ai/speech'
 
# Wit.ai api access token
wit_access_token = 'QOPNRA5CX2GKNLVUNFIGULBFAT3WIVMZ'
 
def RecognizeSpeech(AUDIO_FILENAME, num_seconds = 3):
 
    # record audio of specified length in specified audio file
    record_audio(num_seconds, AUDIO_FILENAME)
 
    # reading audio
    audio = read_audio(AUDIO_FILENAME)
 
    # defining headers for HTTP request
    headers = {'authorization': 'Bearer ' + wit_access_token,
               'Content-Type': 'audio/wav'}
 
    # making an HTTP post request
    resp = requests.post(API_ENDPOINT, headers = headers,
                         data = audio)
 
    # converting response content to JSON format
    data = json.loads(resp.content)
 
    # get text from data
    if '_text' in data:
        text = data['_text']
    else:
        text = '-'
 
    # return the text
    return text
 
if __name__ == "__main__":
    argv = sys.argv
    time = 3
    try:
        time = int(argv[1])
    except:
        pass
    text = RecognizeSpeech('myspeech.wav', time)
    print(text)
    #print("\nYou said: {}".format(text))
    