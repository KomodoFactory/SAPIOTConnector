'use strict';
const AWS = require('aws-sdk');
var querystring = require('querystring');
var http = require('http');

const SNS = new AWS.SNS({ apiVersion: '2010-03-31' });

const jsonStart =  "{\"preq_item\":\"1\",\"quantity\":\"";
const jsonMiddle = "\",\"unit\":\"L\",\"doc_type\":\"NB\",\"material\":\"100-806\",\"plant\":\"1000\",\"deliv_date\":\"";
const jsonEnd = "\"}";
/*
* Sends the Post request
*/
function postCode(codestring) {
  // An object of options to indicate where to post to
  var post_options = {
      host: 'saped1.bemaso.com',
      port: '8010',
      path: '/sap/bc/z_alexa_banf',
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
          'Content-Length': Buffer.byteLength(codestring)
      }
  };

  // Set up the request
  var post_req = http.request(post_options, function(res) {
      res.setEncoding('utf8');
      res.on('data', function (chunk) {
          console.log('Response: ' + chunk);
      });
  });

  // post the data
  post_req.write(codestring);
  post_req.end();

}

function formatDate(deliveryDate){
    var year = ('0' + deliveryDate.getFullYear()).slice(-4);
    var month = ('0' + (deliveryDate.getMonth()+1)).slice(-2);
    var day = ('0' + deliveryDate.getDate()).slice(-2);
    var formatedDate = year + month + day;
    return formatedDate;
}

/*
 *  Concat Json String!
 */
function concatJson(quantity, deliveryDate){
    var jsonString = jsonStart.concat(quantity).concat(jsonMiddle).concat(formatDate(deliveryDate)).concat(jsonEnd);
    return jsonString;
};

/**
 * The following JSON template shows what is sent as the payload:
 *  
 * A "LONG" clickType is sent if the first press lasts longer than 1.5 seconds.
 * "SINGLE" and "DOUBLE" clickType payloads are sent for short clicks.
 *
 * For more documentation, follow the link below.
 * http://docs.aws.amazon.com/iot/latest/developerguide/iot-lambda-rule.html
 */
exports.handler = (event, context, callback) => {
    console.log(event.clickType)

    var deliveryDate = new Date();
    deliveryDate.setDate(deliveryDate.getDate() + 7);
    var quantity;
    if(event.clickType == "SINGLE"){
        quantity = 1;
        var json = concatJson(quantity, deliveryDate);
        console.log(json);
        //postCode(json);
    }else if(event.clickType == "DOUBLE"){
        quantity = 20;
        var json = concatJson(quantity, deliveryDate);
        postCode(json);
    }else{
        console.log('Event not supported');
    }
}
