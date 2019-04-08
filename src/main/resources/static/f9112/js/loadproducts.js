function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;

}

$(function() {
    var categoryId = GetQueryString("categoryId");
    $.ajax({
        url: "http://localhost:8080/catalog/category/" + categoryId,
        type: "get",
        success: function(data) {
            var productsinfo = document.getElementById("products")

            function setDiv(item) {
                var div = '<div class="col-md-3 pro-1">\
                <div class="col-m">\
                <a  href="#" data-toggle="modal" data-target="#myModal1" class="offer-img">\
                <img src=' + item.description + ' class="img-responsive" >\
                </a>\
                <div class="mid-1">\
                <div class="women">\
                <h6>\
                <a href="single.html" >' + item.name + '</a>\
                </h6>\
                </div>\
                <div class="mid-2">\
                <p><em class="item_price">' + item.price + '</em></p>\
                <div class="block">\
                <div class="starbox small ghosting"> </div>\
                </div>\
                <div class="clearfix"></div>\
                </div>\
                <div class="add">\
                <button class="btn btn-danger my-cart-btn my-cart-b" data-id=' + item.productId + ' data-name=' + item.name + ' data-summary="summary 1" data-price=' + item.price + ' data-quantity="1" data-image=' + item.description + '>Add to Cart</button>\
                </div>\
                </div>\
                </div>\
                </div>'
                return div
            }

            function getData() {
                var html = ''
                for (var i = 0; i < data.length; i++) {
                    html += setDiv(data[i])
                }
                productsinfo.innerHTML = html
                console.log(html)
            }
            window.onload = getData()
            var goToCartIcon = function($addTocartBtn) {
                var $cartIcon = $(".my-cart-icon");
                var $image = $('<img width="30px" height="30px" src="' + $addTocartBtn.data("image") + '"/>').css({
                    "position": "fixed",
                    "z-index": "999"
                });
                $addTocartBtn.prepend($image);
                var position = $cartIcon.position();
                $image.animate({
                    top: position.top,
                    left: position.left
                }, 500, "linear", function() {
                    $image.remove();
                });
            }
            $('.myb').click(function() {
                alert("hello world")
            });
            $('.my-cart-btn').myCart({
                classCartIcon: 'my-cart-icon',
                classCartBadge: 'my-cart-badge',
                affixCartIcon: true,
                checkoutCart: function(products) {
                    $.each(products, function() {
                        console.log(this);
                    });
                },
                clickOnAddToCart: function($addTocart) {
                    goToCartIcon($addTocart);
                },
                getDiscountPrice: function(products) {
                    var total = 0;
                    $.each(products, function() {
                        total += this.quantity * this.price;
                    });
                    return total * 1;
                }
            });

        }

    });
})