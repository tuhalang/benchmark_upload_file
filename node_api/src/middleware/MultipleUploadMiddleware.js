const util = require("util");
const path = require("path");
const multer = require("multer");

// Khởi tạo biến cấu hình cho việc lưu trữ file upload
let storage = multer.diskStorage({
  // Định nghĩa nơi file upload sẽ được lưu lại
  destination: (req, file, callback) => {
    callback(null, path.join(`${__dirname}/../../uploadResults`));
  },
  filename: (req, file, callback) => {
    let filename = `${Date.now()}-${file.originalname}`;
    callback(null, filename);
  }
});

let uploadManyFiles = multer({storage: storage}).array("many-files", 17);

// Mục đích của util.promisify() là để bên controller có thể dùng async-await để gọi tới middleware này
let multipleUploadMiddleware = util.promisify(uploadManyFiles);

module.exports = multipleUploadMiddleware;
