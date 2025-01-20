var exec = require('cordova/exec');

exports.getSMS = function(success, error) {
    exec(success, error, 'SmsReader', 'getSMS', []);
};
