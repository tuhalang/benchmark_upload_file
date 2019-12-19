const multipleUploadMiddleware = require("../middleware/MultipleUploadMiddleware");
const logger = require("../log/winston");

let multipleUpload = async (req, res) => {
  
  let startTime = Date.now();
  req.files.manyfiles.forEach(file => {
    file.mv('/home/hungpv/Documents/benchmark_upload_file/node_api/uploadResults/'+ Date.now() + file.name, function(error){
      if(error != undefined)
        console.log(error);
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