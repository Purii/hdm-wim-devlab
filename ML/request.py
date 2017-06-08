import urllib2
# Bei Python 3+, import urllib statt urllib2

import json

class Request:
    def __init__(self,User,Dokument):
        self.Dokument = Dokument
        self.User = User

    def RequestFunktion(self ,User, Dokument):

        data =  {

            "Inputs": {
             "input1": {
                "ColumnNames": [
                    "User",
                    "Dokument"
                ],
                "Values": [
                    [
                        User,
                        Dokument
                    ],

                ]
            }
        },
        "GlobalParameters": {}
    }

        body = str.encode(json.dumps(data))

        url = 'https://ussouthcentral.services.azureml.net/workspaces/ae694347455a4370baa26438bba6b091/services/0f15382be16b4441986aae2b1303cae7/execute?api-version=2.0&details=true'
        api_key = '1qQn23Y1QnUqNIhCPFTju1wPjvv/wny/p7VF6wV64zeZhTWqUVyoJBpz1LMHuGJmR3dVici/HrdNs48kn8Ch8g=='
        headers = {'Content-Type':'application/json', 'Authorization':('Bearer '+ api_key)}

        req = urllib2.Request(url, body, headers)

        try:
            response = urllib2.urlopen(req)

            # Python 3+:  urllib2 ersetzen mit urllib.request:
            # req = urllib.request.Request(url, body, headers)
            # response = urllib.request.urlopen(req)

            result = response.read()
            print(result)
        except urllib2.HTTPError, error:
            print("The request failed with status code: " + str(error.code))


            print(error.info())

            print(json.loads(error.read()))

if __name__ == "__main__":
    xd = Request("Erik", "DaimlerDoku")
    xd.RequestFunktion("Erik", "DaimlerDoku")