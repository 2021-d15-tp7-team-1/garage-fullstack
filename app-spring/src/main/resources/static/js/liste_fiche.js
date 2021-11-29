/**
	Gestion du tri des fiches par 6 boutons radio
 */
(function() {
	const valideCells = document.querySelectorAll(".valideCell");
	const etatCells = document.querySelectorAll(".etatCell");

	const vCheckTout = document.getElementById("validiteTout");
	const vRadioValide = document.getElementById("validiteValide");
	const vRadioAnnulee = document.getElementById("validiteAnnulee");
	
	const eCheckTout = document.getElementById("etatTout");
	const eRadioCloturee = document.getElementById("etatCloturee");
	const eRadioOuverte = document.getElementById("etatOuverte");
		
	vCheckTout.checked = true;
	eCheckTout.checked = true;
	
	
	/**
	Gestion du clic sur les 2 boutons radio "tout"
	 */
	const setEventListenerAndCallBackToRadioBtn_tout = (
		targetedRadioBtn_tout,
		event,
		oppositeRadioBtn_tout,
		browsedCells,
		oppositeBrowsedCells,
		oppositeRadioButton1,
		oppositeRadioButton1_text,
		oppositeRadioButton2,
		oppositeRadioButton2_text) => {
			
			targetedRadioBtn_tout.addEventListener(
				event,
				() => {
					if(oppositeRadioBtn_tout.checked) {
						browsedCells.forEach(td => td.parentNode.removeAttribute("hidden"));
					}
					
					else if(oppositeRadioButton1.checked) {
						oppositeBrowsedCells.forEach(td => 
							td.innerText == oppositeRadioButton1_text 
							? td.parentNode.removeAttribute("hidden") 
							: td.parentNode.setAttribute("hidden", "hidden"));
					}
					
					else if(oppositeRadioButton2.checked) {
						oppositeBrowsedCells.forEach(td => 
							td.innerText == oppositeRadioButton2_text 
							? td.parentNode.removeAttribute("hidden") 
							: td.parentNode.setAttribute("hidden", "hidden"));
					}
				}
			);
	}
	
	setEventListenerAndCallBackToRadioBtn_tout(
		vCheckTout,
		"click",
		eCheckTout,
		valideCells,
		etatCells,
		eRadioCloturee, "Clôturée",
		eRadioOuverte, "Ouverte");
		
	setEventListenerAndCallBackToRadioBtn_tout(
		eCheckTout,
		"click",
		vCheckTout,
		etatCells,
		valideCells,
		vRadioValide, "Valide",
		vRadioAnnulee, "Annulée");
	

	/**
	Gestion du clic sur les boutons radio "valide", "annulée", "clôturée" et "ouverte"
	 */
	const setEventListenerAndCallBackToRadioBtn = (
		targetedRadioButton,
		targetedRadioButton_text,
		event,
		browsedCells,
		lookedAtCellClass,
		oppositeRadioBtn_tout,
		oppositeRadioButton1,
		oppositeRadioButton1_text,
		oppositeRadioButton2,
		oppositeRadioButton2_text) => {
			
			targetedRadioButton.addEventListener(
				event,
				() => {
					if(oppositeRadioBtn_tout.checked) {
						browsedCells.forEach(td => 
							td.innerText == targetedRadioButton_text 
							? td.parentNode.removeAttribute("hidden") 
							: td.parentNode.setAttribute("hidden", "hidden"));
					}
					
					else if(oppositeRadioButton1.checked) {
						browsedCells.forEach(td =>
							td.innerText == targetedRadioButton_text && td.parentNode.querySelector(lookedAtCellClass).innerText == oppositeRadioButton1_text
							? td.parentNode.removeAttribute("hidden")
							: td.parentNode.setAttribute("hidden", "hidden"));
					}
					
					else if(oppositeRadioButton2.checked) {
						browsedCells.forEach(td =>
							td.innerText == targetedRadioButton_text && td.parentNode.querySelector(lookedAtCellClass).innerText == oppositeRadioButton2_text
							? td.parentNode.removeAttribute("hidden")
							: td.parentNode.setAttribute("hidden", "hidden"));
					}
				}
			);
		
		}
	
	setEventListenerAndCallBackToRadioBtn(
		vRadioValide, "Valide",
		"click",
		valideCells,
		".etatCell",
		eCheckTout,
		eRadioCloturee, "Clôturée",
		eRadioOuverte, "Ouverte");
		
	setEventListenerAndCallBackToRadioBtn(
		vRadioAnnulee, "Annulée",
		"click",
		valideCells,
		".etatCell",
		eCheckTout,
		eRadioCloturee, "Clôturée",
		eRadioOuverte, "Ouverte");
		
	
	setEventListenerAndCallBackToRadioBtn(
		eRadioCloturee, "Clôturée",
		"click",
		etatCells,
		".valideCell",
		vCheckTout,
		vRadioValide, "Valide",
		vRadioAnnulee, "Annulée");
		
	setEventListenerAndCallBackToRadioBtn(
		eRadioOuverte, "Ouverte",
		"click",
		etatCells,
		".valideCell",
		vCheckTout,
		vRadioValide, "Valide",
		vRadioAnnulee, "Annulée");
	
})();
