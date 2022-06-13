async function obtenerDatos() {
    const response = await fetch("http://localhost:8080/buscador/");
    const json = await response.json();

    //console.log(json);
}

obtenerDatos()


fetch("http://localhost:8080/buscador/")
    .then(function(response){
        return response.json();
    })
    .then(function(todos){
        let placeholder = document.querySelector("#data-output");
        let out = "";
        for(let documento of todos){
            out += `
         <tr>
            <td>${documento.id}</td>
            <td>${documento.name}</td>
         </tr>
      `;
        }

        placeholder.innerHTML = out;
    });