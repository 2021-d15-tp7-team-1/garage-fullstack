(function() {
	let httpRequest = new XMLHttpRequest();
	const source = document.getElementById("rechercheClient");
	const cible = document.getElementById("clientsTrouves");
	
	let displayClientsAccordingToPressedKeys = () => {
		if(httpRequest.readyState === XMLHttpRequest.DONE) {
			if(httpRequest.status === 200) {
				const jsonResponse = JSON.parse(httpRequest.responseText);
				let clientResponse = "";
					
				jsonResponse.forEach(o => {
					if((o["nom"].toLowerCase().startsWith(source.value.toLowerCase()) || o["prenom"].toLowerCase().startsWith(source.value.toLowerCase())) && source.value != "") {
						clientResponse += '<input type="radio" name="client" value="' + o["id"] + '" />' + o["nom"] + ' ' + o["prenom"] + '<br/>';
					}
				});
				
				cible.innerHTML = clientResponse;
			}	
		}	
	}
	
	let findClients = () => {
		httpRequest = new XMLHttpRequest();
		httpRequest.onreadystatechange = displayClientsAccordingToPressedKeys;
		httpRequest.open('GET', 'http://localhost:8090/rest/clients/all', true);
		httpRequest.send();
	}
	
	source.addEventListener("keydown", findClients);
})();