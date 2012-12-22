// Q: Describe what these functions do using line comments

// This will show an alert dialog with an OK button
function alertValue(value) {
	window.alert(value);
}

// This scrolls the introduction away and shows the test element
function beginTheTest() {
	YUI().use('transition', function(Y) {

		// Shrink to nothing.
		Y.one('#introduction').transition({
			duration : 1, // seconds
			easing : 'ease-out', // CSS syntax
			height : 0,
			top : '100px',

			width : {
				delay : 1,
				duration : 0.5,
				easing : 'ease-in',
				value : 0
			},

			left : {
				delay : 1,
				duration : 0.5,
				easing : 'ease-in',
				value : '150px'
			},

			opacity : {
				delay : 1.5,
				duration : 0.25,
				value : 0
			}
		}, function() {
			this.remove();
			Y.one('#theTest').show(true);
		});
		
		
	});
}

// Shows a modal dialog with "I did it!" as test and an OK button
function buttonClicked() {
	alert("I did it!");
}

function boxButtonClicked(e) {
	var tb = document.getElementById('textbox');
	var q = document.getElementById('questions');
	q.removeChild(tb);
}
