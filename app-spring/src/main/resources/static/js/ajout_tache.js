(function() {
	let httpRequest;
	const source = document.getElementById("recherchePiece");
	const cible = document.getElementById("piecesTrouvees");
	const piecesAjoutees = document.getElementById("piecesAjoutees");
	const fieldSetPiecesAjoutees = document.getElementById("fieldSetPiecesAjoutees");
	
	let displayChosenPieceCheckbox = (value, text) => {
		fieldSetPiecesAjoutees.removeAttribute("hidden")
		let elt = document.createElement('input');
		elt.setAttribute("type", "checkbox");
		elt.setAttribute("checked", "checked");
		elt.setAttribute("name", "piecesNecessaires");
		elt.setAttribute("value", value);
		piecesAjoutees.insertAdjacentElement("beforeend", elt);
		piecesAjoutees.insertAdjacentHTML("beforeend", "<span class='ml-1'>" + text + "</span><br/>");
	};
	
	let displayPiecesAccordingToPressedKeys = () => {
		if(httpRequest.readyState === XMLHttpRequest.DONE) {
			if(httpRequest.status === 200) {
				const jsonResponse = JSON.parse(httpRequest.responseText);
				let clientResponse = "";
					
				if(source.value != "") {
					jsonResponse.forEach(p => {
						if(p["nomPiece"].toLowerCase().startsWith(source.value.toLowerCase())) {
							clientResponse += '<button type="button" class="btn btn-secondary mr-1" name="piecesNecessaires" value="' + p["id"] + '">' + p["nomPiece"] + '</button>';
							cible.innerHTML = clientResponse;
						}
					});
					
					for(let i=0; i<cible.children.length; i++) {
						cible.children[i].addEventListener("click", (e) => displayChosenPieceCheckbox(e.currentTarget.value, e.currentTarget.innerText));
					}
					
				} else {
					cible.innerHTML = "";
				}
				
			}	
		}	
	}
	
	let findPieces = () => {
		httpRequest = new XMLHttpRequest();
		httpRequest.onreadystatechange = displayPiecesAccordingToPressedKeys;
		httpRequest.open('GET', 'http://localhost:8090/rest/pieces/all', true);
		httpRequest.send();
	}

	source.addEventListener("keydown", findPieces);
})();