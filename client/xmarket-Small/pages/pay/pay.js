
var app = getApp();
var user = app.User.get();
var self = null;

var winAjax = {
    cartData : function(){
      self.setData(app.Cart.getCheckData());
    }
}



Page({
  data: {
    market : {},
    goodData : [],
    checkTotal :{
      totalNum : 0,
      totalMoney : 0.0
    },
    status : {
      tabActive : 0
    }
  },
  onLoad: function () {
    // 初始化信息
    self = this

    // 加载店铺信息
    this.setData({
      market : user.market
    });
    
    // 加载购物车数据
    this.ajax.cartData();

    console.log(this.data);
  },
  onTab : function(e){
    self.setData({
      status: {
        tabActive : e.target.dataset.tabactive
      }
    })
  },
  ajax : winAjax
})