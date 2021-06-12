var mongoose = require('mongoose'),
    deviceSchema = require('./device'),
    APIError = require('../APIError');

deviceSchema.statics.createDevice = function(deviceData, callback) {
	
    var newMessage = new model({
        name: deviceData.name,
        id: deviceData.id,
        state: deviceData.state,
		type: deviceData.type
    });

    newMessage.save(function(err) {
      if (err) return callback(err);
      return callback(null);
    });
};

deviceSchema.statics.getAllDevices = function(callback) {
    model.find({}, function(err, found) {
        if (found.length === 0) {
            return callback(null, APIError.NO_DEVICES);
        }
        var response = [];

        found.forEach(function (object) {
            response.push({
                name: object.name,
                id: object.id,
                state: object.state
            })
        });

        return callback(response, null);
    });
};

deviceSchema.statics.getDeviceDetailsById = function(id, callback) {
    model.findOne({id: id}, function(err, found) {
        if (!found) {
            return callback(null, APIError.NO_AVAILABLE_DEVICE);
        }
		return callback(found, null);
    });
};

deviceSchema.statics.changeDeviceState = function(id, state,callback) {
    model.findOneAndUpdate({id: id}, {$set: {state: state}},{new: true}, function(err, result) {
        if (err) {
            return callback(null, APIError.NO_AVAILABLE_DEVICE);
        }
        console.log (result);
		return callback(result, null);
    });
};

deviceSchema.statics.deleteDevice = function(id, callback) {
    model.findOneAndRemove({id: id}, function(err, found) {
        return callback(found, err);
    });
};

var model = mongoose.model('device', deviceSchema);

module.exports = model;
