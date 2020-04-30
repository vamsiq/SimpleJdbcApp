var list = [];
var table = "";
var quantity = {};

function getData() {
    var key = document.getElementById("search").value;
    var request = new XMLHttpRequest()
    request.open('GET', '/products?key=' + key, true)
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.onload = function() {
        var data = JSON.parse(this.response)
        if (request.status == 200) {
            table = "<tr>"
            table += "<th> Select </th>";
            table += "<th> product Code </th>";
            table += "<th> product Name </th>";
            table += "<th> product Price </th>";
            table += "<th> product Gst </th>";
            table += "</tr>";
            data.forEach(product => {
                table += "<tr>"
                if (list.includes(Number(product.productCode))) {
                    table += "<td> <input type='checkbox' onclick='clicked(" + product.productCode + ")' checked></td>";
                } else {
                    table += "<td> <input type='checkbox' onclick='clicked(" + product.productCode + ")'></td>";
                }
                table += "<td> " + product.productCode + " </td>";
                table += "<td> " + product.productName + " </td>";
                table += "<td> " + product.productPrice + " </td>";
                table += "<td> " + product.productGst + "% </td>";
                table += "</tr>";
            });
            document.getElementById("table").innerHTML = table;
        } else {
            console.log('Unable to access the data')
        }
    }
    request.send();
}
getData();

function clicked(code) {
    if (list.includes(code)) {
        list.splice(list.indexOf(code), 1);
    } else {
        list.push(code);
    }
    calculate();
}

function calculateTotal(id, value) {
    quantity[id] = value;
    getTotal();
}

var selected;

function getTotal() {
    table = "<tr>"
    table += "<th> product Code </th>";
    table += "<th> product Name </th>";
    table += "<th> product Price </th>";
    table += "<th> product Gst </th>";
    table += "<th> Quantity </th>";
    table += "<th> Gross Total </th>";
    table += "</tr>";
    let total = 0;
    selected.forEach(product => {
        let qnt = 1;
        if (quantity[product.productCode]) { qnt = quantity[product.productCode]; }
        let sum = (((+100 + +product.productGst) * qnt * product.productPrice) / 100);
        table += "<tr>"
        table += "<td> " + product.productCode + " </td>";
        table += "<td> " + product.productName + " </td>";
        table += "<td> " + product.productPrice + " </td>";
        table += "<td> " + product.productGst + "% </td>";
        table += "<td> <input type='number' value='" + qnt + "' style='width:100px;' id='" + product.productCode + "' onchange='calculateTotal(" + product.productCode + ",this.value)'> </td>";
        table += "<td> " + sum + "</td>";
        table += "</tr>";
        total += sum;
    });
    table += "<tr><td></td><td></td><td></td><td></td><th>Total</th><th>" + total + "</th></tr>";
    document.getElementById("billing").innerHTML = table;
}

function calculate() {
    var request = new XMLHttpRequest()
    request.open('POST', '/billing', true)
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.onload = function() {
        selected = JSON.parse(this.response);
        if (request.status == 200) {
            getTotal();
        } else {
            console.log('Unable to access the data');
        }
    }
    request.send(JSON.stringify(list));
}