from flask import Flask

app = Flask(__name__)

@app.route("/")
def hello():
    return "Hello World!"

@app.route("/PythonAp")
def some():
    return "lolwa"



if __name__ == '__main__':
    app.run(debug=True)