(function() {
	const valideCells = document.querySelectorAll(".valideCell");
	const etatCells = document.querySelectorAll(".etatCell");
	
	let queryValidCellInto = (elt) => {return elt.querySelector(".valideCell")};
	let queryEtatCellInto = (elt) => {return elt.querySelector(".etatCell")};
	
	let lc = [];

	const vCheckTout = document.getElementById("validiteTout");
	const vRadioValide = document.getElementById("validiteValide");
	const vRadioAnnulee = document.getElementById("validiteAnnulee");
	
	const eCheckTout = document.getElementById("etatTout");
	const eRadioCloturee = document.getElementById("etatCloturee");
	const eRadioOuverte = document.getElementById("etatOuverte");
	
	
	let enablingDisablingRadioButtons = (checkBox, radioBtn1, radioBtn2) => {
		checkBox.checked = true;
		radioBtn1.disabled = true;
		radioBtn2.disabled = true;
		
		checkBox.addEventListener(
			"change",
			() => {
				if(radioBtn1.checked) radioBtn1.checked = false;
				if(radioBtn2.checked) radioBtn2.checked = false;
				radioBtn1.disabled = !radioBtn1.disabled;
				radioBtn2.disabled = !radioBtn2.disabled;
			}
		);
	}
	
	let isValid = (elt) => {return elt.innerText === "Valide" ? true : false};
	let isOpen = (elt) => {return elt.innertText === "Ouverte" ? true : false};
	
	
	enablingDisablingRadioButtons(vCheckTout, vRadioValide, vRadioAnnulee);
	enablingDisablingRadioButtons(eCheckTout, eRadioCloturee, eRadioOuverte);
	
	
	
	vCheckTout.addEventListener(
		"click", 
		() => {
			if(eCheckTout.checked) valideCells.forEach(c => c.parentNode.removeAttribute("hidden"));
		}
	);
	
	eCheckTout.addEventListener(
		"click", 
		() => {
			if(vCheckTout.checked) etatCells.forEach(c => c.parentNode.removeAttribute("hidden"));
		}
	);
	
	//let isValid = (elt) => {return elt.innerText === "Valide" ? true : false};
	//let isOpen = (elt) => {return elt.innertText === "Ouverte" ? true : false};
	
	//const valideCells = document.querySelectorAll(".valideCell");
	//const etatCells = document.querySelectorAll(".etatCell");
	
	//let queryValidCellsInto = (elt) => {return elt.parentNode.querySelectorAll(".valideCell")};
	//let queryEtatCellsInto = (elt) => {return elt.parentNode.querySelectorAll(".etatCell")};
	
	vRadioValide.addEventListener(
		"click", 
		() => {
			lc = [];
			valideCells.forEach(td => isValid(td) ? lc.push(td.parentNode) : td.parentNode.setAttribute("hidden", "hidden"));
			
			if(eCheckTout.checked) {
				lc.forEach(tr => tr.removeAttribute("hidden"));
				
			} else if(eRadioCloturee.checked) {
				lc.forEach(tr => console.log(queryEtatCellInto(tr).innerText));
				lc.forEach(tr => tr.removeAttribute("hidden"));
				lc.forEach(tr => queryEtatCellInto(tr).innertText == "Ouverte" ? tr.setAttribute("hidden", "hidden") : tr.removeAttribute("hidden"));
				
			} else if(eRadioOuverte.checked) {
				lc.forEach(tr => console.log(queryEtatCellInto(tr).innerText));
				lc.forEach(tr => tr.removeAttribute("hidden"));
				lc.forEach(tr => queryEtatCellInto(tr).innertText != "Ouverte" ? tr.setAttribute("hidden", "hidden") : tr.removeAttribute("hidden"));
			}
		}
	);
	
	vRadioAnnulee.addEventListener(
		"click", 
		() => {
			lc = [];
			valideCells.forEach(td => !isValid(td) ? lc.push(td.parentNode) : td.parentNode.setAttribute("hidden", "hidden"));
			
			if(eCheckTout.checked) {
				lc.forEach(tr => tr.removeAttribute("hidden"));
				
			} else if(eRadioCloturee.checked) {
				lc.forEach(tr => console.log(queryEtatCellInto(tr).innerText));
				lc.forEach(tr => tr.removeAttribute("hidden"));
				lc.forEach(tr => queryEtatCellInto(tr).innerText == "Ouverte" ? tr.setAttribute("hidden", "hidden") : tr.removeAttribute("hidden"));
				
			} else if(eRadioOuverte.checked) {
				lc.forEach(tr => console.log(queryEtatCellInto(tr).innerText));
				lc.forEach(tr => tr.removeAttribute("hidden"));
				lc.forEach(tr => queryEtatCellInto(tr).innertText != "Ouverte" ? tr.setAttribute("hidden", "hidden") : tr.removeAttribute("hidden"));
			}
		}
	);
	

})();