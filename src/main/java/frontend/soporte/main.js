async function obtenerDatos() {
    const response = await fetch("URL_DEL_JSON");
    const json = await response.json();

    console.log(json);
}

obtenerDatos()


fetch("nombreDelJson.json")
    .then(function(response){
        return response.json();
    })
    .then(function(products){
        let placeholder = document.querySelector("#data-output");
        let out = "";
        for(let product of products){
            out += `
         <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
         </tr>
      `;
        }

        placeholder.innerHTML = out;
    });