async function obtenerDatos() {
    const response = await fetch("URL");
    const json = await response.json();

    console.log(json);
}

obtenerDatos()


fetch("products.json")
    .then(function(response){
        return response.json();
    })
    .then(function(products){
        let placeholder = document.querySelector("#data-output");
        let out = "";
        for(let product of products){
            out += `
         <tr>
            <td> <img src='${product.image}'> </td>
            <td>${product.name}</td>
            <td>${product.price}</td>
            <td>${product.inventory}</td>
            <td>${product.productCode}</td>
         </tr>
      `;
        }

        placeholder.innerHTML = out;
    });