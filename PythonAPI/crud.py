from flask import Flask, request, jsonify, Response
import os
from flask import g
import sqlite3
import json
import thread
import time
from werkzeug.utils import secure_filename


DATABASE = "demp.db"

app = Flask(__name__)

i=1

#for file uploads
UPLOAD_FOLDER = os.path.basename('uploads')
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
ALLOWED_EXTENSIONS = set(['txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'])


if not os.path.exists(DATABASE):
    conn = sqlite3.connect(DATABASE)
    cur = conn.cursor()
    cur.execute("CREATE TABLE users (username varchar(30), email varchar(80));")
    conn.commit()
    conn.close()

def chaluRahuDet(a):
    while True:
        global i
        i+=1
        time.sleep(1)

try:
    print "lolwa"
    thread.start_new_thread( chaluRahuDet, ("ppp",) )
except:
   print "Error: unable to start thread"


# Create Connection with Database
def get_db():
    db = getattr(g, '_database', None)
    if db is None:
        db = g._database = sqlite3.connect(DATABASE)
    return db

# CLose Connection with database
@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()

# Initialize Database from python shell for the 1st time
# >>> from yourapplication import init_db
# >>> init_db()
def init_db():
    with app.app_context():
        db = get_db()
        with app.open_resource('schema.sql', mode='r') as f:
            db.cursor().executescript(f.read())
        db.commit()


# endpoint to create new user
@app.route("/user", methods=["POST"])
def add_user():
    username = request.json['username']
    email = request.json['email']
    cur=get_db().cursor()
    res= cur.execute("INSERT into users values(?,?);",(username,email))
    get_db().commit()
    return Response(status=200)
#     Response(js, status=200, mimetype='application/json')


# endpoint to show all users
@app.route("/user", methods=["GET"])
def get_user():
    cur = get_db().cursor()
    res= cur.execute("select * from users")
    items=[]
    for row in res:
        items.append({"username":row[0],"email":row[1]})
    return json.dumps(items)


# endpoint to get user detail by id
@app.route("/user/<id>", methods=["GET"])
def user_detail(id):
    cur = get_db().cursor()
    res = cur.execute("select * from users where username=?;",(id,))
    items = []
    for row in res:
        items.append({"username": row[0], "email": row[1]})
    return json.dumps(items)


# endpoint to update user
@app.route("/user/<id>", methods=["PUT"])
def user_update(id):
    username = request.json['username']
    email = request.json['email']
    cur = get_db().cursor()
    res = cur.execute("Update users set username=?, email=? where username=?;", (username, email,id))
    get_db().commit()
    return Response(status=200)


# endpoint to delete user
@app.route("/user/<id>", methods=["DELETE"])
def user_delete(id):
    cur = get_db().cursor()
    res = cur.execute("delete from users where username=?;", (id, ))
    get_db().commit()
    return Response(status=200)


#File upload test
@app.route('/upload', methods=['POST'])
def upload_file():
    if 'file' not in request.files:
        return Response(status=410)
    file = request.files['file']
    # if user does not select file, browser also
    # submit a empty part without filename
    if file.filename == '':
        return Response(status=490)
    if file and allowed_file(file.filename):
        filename = secure_filename(file.filename)
        file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
    return Response(status=200)

# demo test threading

@app.route("/saang", methods=["GET"])
def time_Saang():
    items=[]
    items.append({'TIME':i})
    return json.dumps(items)

if __name__ == '__main__':
    app.run(debug=True)