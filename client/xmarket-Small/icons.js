

var icons = {
  toUrl : function(icon){
    return this.host + this[icon].u + this[icon] + this.size + this.format;
  },
  host : "http://xmarket.oss-cn-shenzhen.aliyuncs.com/market/app/icon/",
  size : "@3x",
  format : ".png",
  account:{
    "w" : "",
    "h" : "",
    "u" : "accountIcon"
  },
  B_location:{
    "w" : "",
    "h" : "",
    "u" : "B-location"
  },
  O_location:{
    "w" : "",
    "h" : "",
    "u" : "O-location"
  },
  W_locationIcon:{
    "w" : "9px",
    "h" : "13px",
    "u" : "W-locationIcon"
  },
  cart:{
    "w" : "",
    "h" : "",
    "u" : "CarIcon"
  },
  check:{
    "w" : "",
    "h" : "",
    "u" : "checkIcon"
  },
  close:{
    "w" : "",
    "h" : "",
    "u" : "closeIcon"
  },
  focus:{
    "w" : "",
    "h" : "",
    "u" : "FocusIcon"
  },
  loading:{
    "w" : "",
    "h" : "",
    "u" : "loading"
  },
  minus:{
    "w" : "",
    "h" : "",
    "u" : "minusIcon"
  },
  navigate:{
    "w" : "15px",
    "h" : "16px",
    "u" : "navigateiconicon"
  },
  OrderIcon:{
    "w" : "",
    "h" : "",
    "u" : "OrderIcon"
  },
  payment:{
    "w" : "",
    "h" : "",
    "u" : "paymentIcon"
  },
  receipt:{
    "w" : "",
    "h" : "",
    "u" : "ReceiptIcon"
  },
  refunds:{
    "w" : "",
    "h" : "",
    "u" : "RefundsIcon"
  },
  search:{
    "w" : "16px",
    "h" : "16px",
    "u" : "SearchIcon"
  },
  selected:{
    "w" : "",
    "h" : "",
    "u" : "SelectedIcon"
  },
  success:{
    "w" : "",
    "h" : "",
    "u" : "successIcon"
  },
  search:{
    "w" : "",
    "h" : "",
    "u" : "SearchIcon"
  },
  trash:{
    "w" : "",
    "h" : "",
    "u" : "trashIcon"
  }
}




module.exports = function(){
    var imgs = {};
    for(var key in icons){
        if(key == "host" || key == "size" || key == "format") continue;
        imgs[key] = {};
        imgs[key].url = icons.host + icons[key].u + icons.size + icons.format;
        imgs[key].w = icons[key].w;
        imgs[key].h = icons[key].h;
    }
    return imgs;
}