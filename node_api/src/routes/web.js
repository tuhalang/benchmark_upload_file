const express = require("express");
const router = express.Router();
const multipleUploadController = require("../controllers/MultipleUploadController");

let initRoutes = (app) => {
    
  // Upload nhiều file với phương thức post
  router.post("/multiple-upload", multipleUploadController.multipleUpload);

  return app.use("/", router);
};

module.exports = initRoutes;