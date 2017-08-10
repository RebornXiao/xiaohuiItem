const config = require('../../config')
const icons = require('../../icons')

var app = getApp()
var user = app.User.get();
var self = null

var winAjax = {
    marketData : function(){
      var marketData = {} ,currMarket = user.market;
      var data = [{"adminName":"","formatDistance":"0米","formatAddress":"广东省广州市越秀区","address":"淘金路22号","coveringDistance":"1公里","deliveryMode":1,"showPhoneNumber":"1380***8000","name":"越秀·淘金店","id":100001,"hidePhoneNumber":"13800138000","type":1,"deliveryCost":0},{"adminName":"","formatDistance":"0米","formatAddress":"广东省广州市增城市中新镇团结村","address":"御峰四街3号","coveringDistance":"1公里","deliveryMode":1,"showPhoneNumber":"1868***3352","name":"恒大山水城店","id":100000,"hidePhoneNumber":"18680233352","type":1,"deliveryCost":0}];

      for(var i = 0; i < data.length; i++){
        marketData[data[i].id] = data[i];
        if(currMarket != null && currMarket.id == data[i].id){
          marketData[data[i].id].show = false;
        }else{
          marketData[data[i].id].show = true;
        }
      }
      self.setData({
          marketData: marketData
      });

      console.log('marketData'+ JSON.stringify(marketData));
    },

    itemTypes : function(){
        var option = config.itemTypes;
        option.success = function(data){
            self.setData({
                typeList: data.data.response.datas
            })
        };
        app.ajax(self,option);
    },
    goodData : function(){
      var goodData = {}, cart = app.Cart.getCart();
      var data = [{"unitName":"部","maximumSellCount":100,"minimumSellCount":1,"discountPrice":0,"sellPrice":10,"restrictionQuantity":-1,"itemTemplateId":100000,"itemId":100000,"imageUrl":"http://oss.0085.com/supplyerp/1490696078398.jpg","name":"iPhone7","batchesCode":"","discountType":0,"maxPrice":10,"stock":100,"barcode":"885909842612","actualSales":0,"deliveryDelay":0,"status":1,"num":0,"idx":0},{"unitName":"部","maximumSellCount":100,"minimumSellCount":1,"discountPrice":0,"sellPrice":10,"restrictionQuantity":-1,"itemTemplateId":100000,"itemId":100001,"imageUrl":"http://oss.0085.com/supplyerp/1490696078398.jpg","name":"iPhone7","batchesCode":"","discountType":0,"maxPrice":10,"stock":100,"barcode":"885909842612","actualSales":0,"deliveryDelay":0,"status":1,"num":0,"idx":1}];

      for(var i = 0; i < data.length; i++){
        if(cart[data[i].itemId]){
          data[i].num = cart[data[i].itemId].num;
          data[i].check = cart[data[i].itemId].check;
        }else{
          data[i].num = 0;
          data[i].check = false;
        }

        goodData[data[i].itemId] = data[i];

      }
  
      self.setData({
          goodData: goodData,
          cartTotal : cart.cartTotal
      })
    },
    cartData : function(){
      var cartData = app.Cart.getCart();
      delete cartData.cartTotal;
      return cartData;
    }
}

Page({
  data: {
    location : {},
    marketData : [],
    currMarket : false,
    typeList : [{"image":"http://oss.0085.com/supplyerp/1490943599954.jpg","name":"电子/数码","icon":"http://oss.0085.com/2017/0112/supplychaint/14841891670002.jpeg","id":100000,"fillImage":1,"subItemTypes":[{"image":"http://oss.0085.com/supplyerp/1490943792006.jpg","name":"手机","icon":"http://oss.0085.com/supplyerp/1490943850667.jpg","id":100001,"fillImage":0},{"image":"http://oss.0085.com/2017/0112/supplychaint/14841891670002.jpeg","name":"数据线","icon":"http://oss.0085.com/2017/0112/supplychaint/14841891670002.jpeg","id":100002,"fillImage":0},{"image":"http://oss.0085.com/2017/0112/supplychaint/14841891190230.jpeg","name":"数码配件","icon":"http://oss.0085.com/2017/0112/supplychaint/14841891190230.jpeg","id":100003,"fillImage":0},{"image":"","name":"影音娱乐","icon":"","id":100018,"fillImage":0}]},{"image":"http://supplymc.0085.com/static/img/homepage/type5.png","name":"特价酒水","icon":"http://supplymc.0085.com/static/img/homepage/type5.png","id":100019,"fillImage":1,"subItemTypes":[{"image":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90044.jpg","name":"啤酒","icon":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90044.jpg","id":100020,"fillImage":0},{"image":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90045.jpg","name":"劲酒","icon":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90045.jpg","id":100021,"fillImage":0},{"image":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90050.jpg","name":"红酒","icon":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90050.jpg","id":100022,"fillImage":0},{"image":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90051.jpg","name":"洋酒","icon":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90051.jpg","id":100023,"fillImage":0}]},{"image":"http://supplymc.0085.com/static/img/homepage/type1.png","name":"零食糖果","icon":"http://supplymc.0085.com/static/img/homepage/type1.png","id":100004,"fillImage":1,"subItemTypes":[{"image":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT11013.jpg","name":"口香糖","icon":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT11013.jpg","id":100005,"fillImage":0},{"image":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT11015.jpg","name":"花生瓜子","icon":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT11015.jpg","id":100006,"fillImage":0},{"image":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90055.jpg","name":"糖果","icon":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90055.jpg","id":100007,"fillImage":0},{"image":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90056.jpg","name":"饼干膨化","icon":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90056.jpg","id":100008,"fillImage":0},{"image":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90062.jpg","name":"进口产品","icon":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90062.jpg","id":100009,"fillImage":0}]},{"image":"http://supplymc.0085.com/static/img/homepage/type3.png","name":"日用洗护","icon":"http://supplymc.0085.com/static/img/homepage/type3.png","id":100013,"fillImage":1,"subItemTypes":[{"image":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT16003.jpg","name":"计生日用","icon":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT16003.jpg","id":100014,"fillImage":0},{"image":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT16006.jpg","name":"洗护类","icon":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT16006.jpg","id":100015,"fillImage":0},{"image":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90027.jpg","name":"纸类","icon":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90027.jpg","id":100016,"fillImage":0},{"image":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90046.jpg","name":"牙膏牙刷","icon":"http://1life.oss-cn-shenzhen.aliyuncs.com/supplychain/item_template/GT90046.jpg","id":100017,"fillImage":0}]}],
    goodData : [],
    cartData : [],
    cartTotal :{
      totalNum : 0,
      totalMoney : 0.0
    },
    checkTotal :{
      speciesNum : 0,
      totalNum : 0,
      totalMoney : 0.0
    },
    status : {
      cartShow : false,
      typeShow : 0,
      typeSubShow : 0,
      checkAll : false
    },
    temps : {
      temp_main : "temp_hot"
    },
    active : 0,
    subActive: 0,
    showTemp : 'temp_Goods',
    isCanvas : false,
    showCanvasTemp : 'temp_offcanvas_navbar',
    icons : icons()
  },
  onLoad: function () {
    // 初始化信息
    self = this
    // 获取位置
    this.getLocation()
    // 选择店铺
    this.ajax.marketData();
    if(user.market == null){
      self.setData({
          isCanvas: true,
          showCanvasTemp: 'temp_offcanvas_address'
      });
    }else{
      self.setData({
          currMarket: user.market
      });
    }

    // 加载分类
    this.ajax.itemTypes()

    // 加载商品数据
    this.ajax.goodData()


    // 加载选中商品total
    // self.setData({checkTotal : app.Cart.getCheckTotal()});

  },
  getLocation: function () {
    wx.getLocation({
      success: function (res) {
        user.location = {
            hasLocation: true,
            lot: res.longitude,
            lat : res.latitude
          };
        self.setData({location : user.location});
        app.User.set(user);
      }
    })
  },
  onStatus: function(e){
    var data = {};
    this.data.status[e.target.dataset.statusKey] = e.target.dataset.statusVal ? e.target.dataset.statusVal : (this.data.status[e.target.dataset.statusKey] ? false : true);
    data.status = this.data.status;

    if(e.target.dataset.tempKey && e.target.dataset.tempVal){
      this.data.temps[e.target.dataset.tempKey] = e.target.dataset.tempVal;
      data.temps = this.data.temps;
    }

    // 特殊处理
    if(e.target.dataset.statusKey == 'cartShow' && this.data.status.cartShow){
      // 加载购物车数据
      data.cartData = this.ajax.cartData();
      data.checkTotal = app.Cart.getCheckTotal();
    }

    self.setData(data);
  },
  onCartCheckAll : function(e){
    self.setData(app.Cart.CHECKALL(this.data, e.target.dataset.check));
  },
  onCart : function(e){
    var data = {};
    data.status = this.data.status;
    data.goodData = this.data.goodData;
    data.cartData = this.data.cartData;
    data.cartTotal = this.data.cartTotal;
    data.checkTotal = this.data.checkTotal;
    self.setData(app.Cart[e.target.dataset.type](data, e.target.dataset.id));
  },
  ajax : winAjax,


  onTarget: function(e){
    self.setData({
      active: e.target.dataset.idx,
      showTemp: e.target.dataset.temp
    })
  },
  onSubTarget: function(e){
    self.setData({
      subActive: e.target.dataset.idx,
      showTemp: e.target.dataset.temp
    })
  },
  onCanvas : function(e){
    self.setData({
      isCanvas: this.data.isCanvas ? false : true,
      showCanvasTemp: e.target.dataset.temp
    })
  },
  onMarket : function(e){
    for(var key in this.data.marketData){
      this.data.marketData[key].show = true;
    }

    this.data.marketData[e.target.dataset.id].show = false;
    user.market = this.data.marketData[e.target.dataset.id];
    app.User.set(user);
    self.setData({
      marketData : this.data.marketData,
      currMarket : user.market,
      isCanvas: false
    })
  },
  off : function(){}
})



