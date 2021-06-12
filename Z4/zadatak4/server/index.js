var express = require("express");
var app = express();
var bodyParser = require('body-parser');
var service = require('./service');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.urlencoded({ extended: false }))

var port = process.env.PORT || 8080;
// Create the express router object for Photos
var router = express.Router();
var mongoose   = require('mongoose');
mongoose.connect("mongodb://localhost:27017/devices");

/*get all devices*/
router.get('/devices/', service.getDevices);

/*get device details by id*/
router.get('/device/:id', service.getDeviceDetails);

/*create device*/
router.post('/device', service.createDevice);

router.delete('/device/:id', service.deleteDevice);

/*change state */
router.post('/device/:id/:state', service.changeDeviceState);

app.use("/api", router);
app.listen(port);
console.log('Smart Hospital server is running on port' + port);