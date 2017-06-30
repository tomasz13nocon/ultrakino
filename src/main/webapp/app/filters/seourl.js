angular.module("app")
.filter("seourl", function() {
	return function(link) {
		if (typeof link === 'undefined')
			return '';
		var chars = {
			'ą': 'a',
			'Ą': 'A',
			'ć': 'c',
			'Ć': 'C',
			'ę': 'e',
			'Ę': 'E',
			'ł': 'l',
			'Ł': 'L',
			'ń': 'n',
			'Ń': 'N',
			'ó': 'o',
			'Ó': 'O',
			'ś': 's',
			'Ś': 'S',
			'ź': 'z',
			'Ź': 'Z',
			'ż': 'z',
			'Ż': 'Z',
			' ': '-',
		};
		return link.replace(/[ąĄćĆęĘłŁńŃóÓśŚźŹżŻ ]/g, function(match) {
			return chars[match];
		});
	};
});
