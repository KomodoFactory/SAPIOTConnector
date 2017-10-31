'use strict';
var http = require('http');

const jsonStart =  "{\"doc_type\":\"NB\",\"preq_item\":\"1\",\"quantity\":\"";
const jsonMiddle = "\",\"unit\":\"L\",\"material\":\"100-806\",\"plant\":\"1000\",\"deliv_date\":\"";
const jsonEnd = "\"}";

const hostAddress = 'saped1.bemaso.com';
const port = '8010';
const pathAddress = '/sap/bc/z_alexa_banf';


function generatePostOptions(codestring){
    return {
      host: hostAddress,
      port: port,
      path: pathAddress,
      method: 'POST',
      headers: {
          'Content-Type': 'application/json',
          'Content-Length': Buffer.byteLength(codestring)
      }
    };
}

function generatePostRequest(post_options){
    return http.request(post_options, function(res) {
        res.setEncoding('utf8');
        res.on('data', function (chunk) {
            console.log('Response: ' + chunk);
        });
    });
}

function sendRequest(codestring) {
  var post_options = generatePostOptions(codestring);
  var post_req = generatePostRequest(post_options);
  post_req.write(codestring);
  post_req.end();
  
  
    console.log(post_options);
  
}

function formatDate(deliveryDate){
    var year = ('0' + deliveryDate.getFullYear()).slice(-4);
    var month = ('0' + (deliveryDate.getMonth()+1)).slice(-2);
    var day = ('0' + deliveryDate.getDate()).slice(-2);
    var formatedDate = year + month + day;
    return formatedDate;
}

function generateJson(quantity, deliveryDate){
    return jsonStart.concat(quantity).concat(jsonMiddle).concat(formatDate(deliveryDate)).concat(jsonEnd);
};


exports.handler = (event, context, callback) => {
    var deliveryDate = new Date();
    var quantity;
    var json;
    if(event.clickType == "SINGLE"){
        deliveryDate.setDate(deliveryDate.getDate() + 14);
        quantity = 10;
        json = generateJson(quantity, deliveryDate)
    }else if(event.clickType == "DOUBLE"){
        deliveryDate.setDate(deliveryDate.getDate() + 14);
        quantity = 100;
        json = generateJson(quantity, deliveryDate)
    }else if(event.clickType == "LONG"){
        deliveryDate.setDate(deliveryDate.getDate() + 7);
        quantity = 10;
        json = generateJson(quantity, deliveryDate)
    }
    sendRequest(json);
}