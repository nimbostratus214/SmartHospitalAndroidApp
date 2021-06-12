var deviceModel = require('./models/model'),
    APIError = require('./APIError');

exports.createDevice = function (req, res, next) {
	console.log(req.body)
	if (req.body) {
		deviceModel.createDevice(req.body, function (err) {
			if (err) {
				console.log(err)
				res.status(400);
				res.send(APIError.INVALID_PARAMS);
			} else {
				res.send(APIError.OK);
			}
		});
	} else {
		res.send(APIError.NO_BODY);
	}
};

exports.getDevices = function (req, res, next) {
	console.log('Getting devices...')
	if (req.body) {
		deviceModel.getAllDevices(function (resp, err) {
			if (err) {
				res.status(400);
				res.send(err);
			} else {
				res.send(resp);
			}
		});
	} else {
		res.send(APIError.NO_BODY);
	}
};

exports.getDeviceDetails = function (req, res, next) {
	console.log('Getting device details... ' + req.params.id)
	if (req.params.id) {
		deviceModel.getDeviceDetailsById(req.params.id, function (resp, err) {
			if (err) {
				res.status(400);
				res.send(err);
			} else {
				res.send(resp);
			}
		});
	} else {
		res.send(APIError.NO_BODY);
	}
};

exports.changeDeviceState = function (req, res, next) {
	console.log('Changing the device state... ' + req.params.id+ ',new state: ', req.params.state)
	if (req.params.id) {
		deviceModel.changeDeviceState(req.params.id, req.params.state, function (resp, err) {
			if (err) {
				res.status(400);
				res.send(err);
			} else {
				res.send(resp);
			}
		});
	} else {
		res.send(APIError.NO_BODY);
	}
};

exports.deleteDevice = function (req, res, next) {
    console.log('Deleting device with id... ' + req.params.id)
	if (req.params.id) {
		deviceModel.deleteDevice(req.params.id, function (resp, err) {
			if (err) {
				res.status(400);
				res.send(err);
			} else {
				res.send(resp);
			}
		});
	} else {
		res.send(APIError.NO_HEADER_PARAMS);
	}
};

