var mongoose     = require('mongoose');
var Schema       = mongoose.Schema;

var DeviceSchema   = new Schema({
	
    name: {
        type: String
    },
	
	id: {
        type: String,
        required: true,
		unique: true
    },
	state: {
		type: String
	},
	type: {
		type: String
	}
	
});

module.exports = DeviceSchema;