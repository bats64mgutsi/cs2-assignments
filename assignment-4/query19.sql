SELECT productName FROM `products` WHERE quantityInStock  = (SELECT MAX(quantityInStock) FROM `products`);