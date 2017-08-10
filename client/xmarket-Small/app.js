const openIdUrl = require('./config').openIdUrl
const duration = 2000

var CACHE = {
  cart : "CACHE_BUYCAR",
  user : "CACHE_USER",
}

//app.js
App({
  onLaunch: function() {
    //调用API从本地缓存中获取数据
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
  },
  onShow: function () {
    console.log('App Show')
  },
  onHide: function () {
    console.log('App Hide')
  },
  globalData: {
    hasLogin: false,
    openid: null
  },

  getUserInfo: function(cb) {
    var that = this
    if (this.globalData.userInfo) {
      typeof cb == "function" && cb(this.globalData.userInfo)
    } else {
      //调用登录接口
      wx.getUserInfo({
        withCredentials: false,
        success: function(res) {
          that.globalData.userInfo = res.userInfo
          typeof cb == "function" && cb(that.globalData.userInfo)
        }
      })
    }
  },

  globalData: {
    userInfo: null
  },
  /** 全局请求 **/
  ajax: function(self,option){
    self.setData({loading: true})
    wx.request({
      url: option.url,
      data: option.data,
      success: function(result) {
        option.success(result);
        self.setData({
          loading: false
        })
      },
      fail: function({errMsg}) {

        // wx.showModal({
        //   title: '请求失败提示',
        //   content: errMsg
        // })
        console.log('request fail', errMsg)
        self.setData({loading: false})
      }
    })
  },

  /** 用户数据 **/
  User : {
    CACHE_OPTION:{
      location : {hasLocation: false, lot : 0, lat : 0},
      market : null,
      info : {}
    },
    getMarket : function(){
      var market = wx.getStorageSync(CACHE.user).market || false;
      if(!market){
        wx.showToast({
          title: '请选择店铺',
          icon: 'success',
          duration: 2000
        })
        return false;
      }
      return market;
    },
    getLocation : function(){
      return wx.getStorageSync(CACHE.user).location || 0;
    },
    get : function(){
      return wx.getStorageSync(CACHE.user) || this.CACHE_OPTION;
    },
    set : function(data){
      wx.setStorage({
        key : CACHE.user,
        data : data,
        success : function(){},
        fail : function(){}
      });
    }

  },

  /** 购物车事件 **/
  Cart : {
    CACHE_NAME : "CACHE_CART",
    CACHE_OPTION:{
        "CarUpTime": 0,         // 上次修改时间:记录上次更新的时间
        "CarUpLoadTime": 0,     // 更新时间:点击事件的时的时间 
        "CarSuccessTime": 0,    // 完成时间:请求服务器完成的时间
        "Market": {
        }
    },
    CACHE_OPTION_MARKET:{
      cartTotal :{
        totalNum : 0,
        totalMoney : 0.0
      }
    },
    ADD : function(data, id){
      var Cart = this.getCart();
      if(!Cart) return false;

      data.goodData[id].check = true;

      if(data.goodData) data.goodData[id].num += 1;
      if(data.cartData && data.cartData[id]) data.cartData[id].num += 1;

      data.cartTotal.totalNum += 1;
      data.cartTotal.totalMoney += data.goodData[id].sellPrice;

      Cart.cartTotal = data.cartTotal;
      this.saveCart(Cart, data.goodData[id]);

      if(data.checkTotal){
        data.checkTotal = this.getCheckTotal(Cart);
      }
      return data;
    },
    MINUS : function(data, id){
      if(data.goodData[id].num == 0) return data;

      var Cart = this.getCart();
      if(!Cart) return false;

      if(data.goodData) data.goodData[id].num -= 1;
      if(data.cartData && data.cartData[id]){
        data.cartData[id].num -= 1;
        if(data.cartData[id].num == 0){
          //  清除购物车数据
          delete data.cartData[id];
          data.checkTotal.speciesNum -= 1;
        }
      }


        
      data.cartTotal.totalNum -= 1;
      data.cartTotal.totalMoney -= data.goodData[id].sellPrice;

      Cart.cartTotal = data.cartTotal;
      this.saveCart(Cart, data.goodData[id]);

      
      if(data.checkTotal){
        data.checkTotal = this.getCheckTotal(Cart);
      }
      return data;
    },
    CHECK : function(data, id){
      var Cart = this.getCart();
      if(!Cart) return false;

      data.cartData[id].check = !data.cartData[id].check;
      Cart[id].check = data.cartData[id].check;

      this.saveCart(Cart, data.cartData[id]);
      data.checkTotal = this.getCheckTotal(Cart);
      return data;
    },
    CHECKALL : function(data, check){
      var Cart = this.getCart(), cartData = {};
      if(!Cart) return false;
      data.status.checkAll = !check;
      for(var key in Cart){
        if(key == 'cartTotal') continue;
        Cart[key].check = !check;
        cartData[key] = Cart[key];
      }

      this.saveCart(Cart, null);
      return {
          cartData: cartData,
          cartTotal : Cart.cartTotal,
          checkTotal : this.getCheckTotal(Cart),
          status : data.status
      };
    },
    REMOVER : function(data){
      this.saveCart(this.CACHE_OPTION_MARKET, null);
      data.status.checkAll = false;
      for(var key in data.cartData){
        data.cartData[key].num = 0;
      }
      return {
          cartData: {},
          cartTotal : this.CACHE_OPTION_MARKET.cartTotal,
          checkTotal : this.CACHE_OPTION_MARKET.cartTotal,
          status : data.status
      };
    },
    getCheckTotal : function(cart){
      var Cart = cart || this.getCart(), checkTotal = {speciesNum : 0, totalNum : 0, totalMoney : 0.0 };
      if(!Cart) return false;

      for(var key in Cart){
        if(key == 'cartTotal') continue;
        if(Cart[key].check){
          checkTotal.speciesNum += 1;
          checkTotal.totalNum += Cart[key].num;
          if(Cart[key].discountType == 1){
            checkTotal.totalMoney += Cart[key].num * Cart[key].discountPrice;
          }else{
            checkTotal.totalMoney += Cart[key].num * Cart[key].sellPrice;
          }
        }
      }
      return checkTotal;
    },
    getCheckData : function(cart){
      var Cart = cart || this.getCart(), checkTotal = {totalNum : 0, totalMoney : 0.0 }, data = {};
      if(!Cart) return false;

      for(var key in Cart){
        if(key == 'cartTotal') continue;
        if(Cart[key].check){
          data[key] = Cart[key];

          checkTotal.totalNum += Cart[key].num;
          if(Cart[key].discountType == 1){
            checkTotal.totalMoney += Cart[key].num * Cart[key].discountPrice;
          }else{
            checkTotal.totalMoney += Cart[key].num * Cart[key].sellPrice;
          }
        }
      }
      return {
          goodData: data,
          checkTotal : checkTotal
      };
    },
    getCart : function(){
      var MarketId = getApp().User.getMarket();
      if(!MarketId) return this.CACHE_OPTION_MARKET;

      try{
        return wx.getStorageSync(CACHE.cart).Market[MarketId];
      }catch(e){
        return this.CACHE_OPTION_MARKET;
      }
    },
    saveCart : function(market, data){
      var cart = this.CACHE_OPTION;
      var MarketId = getApp().User.getMarket();

      if(data != null){
        if(data.num == 0){
          delete market[data.itemId];
        }else{
          market[data.itemId] = data;
        }
      }

      cart.CarUpLoadTime = new Date().getTime();
      cart.Market[MarketId] = market;
      wx.setStorage({
        key : CACHE.cart,
        data : cart,
        success : function(){},
        fail : function(){}
      });
    }
  }
})
























