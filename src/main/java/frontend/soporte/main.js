var busqueda = document.getElementById("query");
busqueda.addEventListener('keypress', obtenerDatos);

/*busqueda.addEventListener('keypress', function (e) {
    if (e.key === 'Enter') {
        // code for enter
    }
});*/

function obtenerDatos(){
    fetch("http://localhost:63342/buscador/?consulta=folio")
        .then(res => res.text())          // convert to plain text
        .then(text => console.log(text))  // then log it out
}

/*function pintarTabla(datos){
    contenido.innerHTML = "";
    for (let valor of datos){
        contenido.innerHTML += `
            <tr>
                <td>${valor.id}</td>
                <td>${valor.name}</td>
            <tr>
        `
    }
}*/

/*fetch("http://localhost:8080/buscador/?consulta="+"folio")
    .then(res => res.json())
    .then(data => console.log(data))*/

const palabra = document.getElementsByName("consulta");

fetch("http://localhost:8080/buscador/?consulta="+palabra)
    .then(function(response){
        const var1 = response.json();
        console.log(var1);
        return
    })
    .then(function(todos){
        const placeholder = document.querySelector("#data-output");
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

/*ejecutar fech

async function obtenerDatos() {
    const response = await fetch("http://localhost:8080/buscador/?consulta="+"folio");
    const json = await response.json();

    console.log(json);
}

obtenerDatos()*/