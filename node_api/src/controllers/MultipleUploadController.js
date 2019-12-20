const multipleUploadMiddleware = require("../middleware/MultipleUploadMiddleware");
const logger = require("../log/winston");

const MongoClient = require('mongodb').MongoClient;
const url = "mongodb://localhost:27017/";

let multipleUpload = async (req, res) => {
  
  let startTime = Date.now();
  req.files.manyfiles.forEach(file => {
    file.mv('/home/hungpv/Documents/benchmark_upload_file/node_api/uploadResults/'+ Date.now() + file.name, function(error){
      if(error != undefined)
        console.log(error);
    });
  });

  MongoClient.connect(url, function(err, db) {
    if (err) throw err;
    var dbo = db.db("mydb");
    var myobj = { name: "log1", message: "12321432143242534543" };
    dbo.collection("log").insertOne(myobj, function(err, res) {
      if (err) throw err;
      console.log("1 document inserted");
      db.close();
    });
  });

  let endTime = Date.now();

  logger.info('Time execute: ' + (endTime - startTime));
  return res.send(`Your files has been uploaded. Time execute: `+(Date.now() - startTime));

};

module.exports = {
  multipleUpload: multipleUpload
};
//https://github.com/grpc/grpc-java/blob/master/examples/src/main/java/io/grpc/examples/helloworld/HelloWorldClient.java