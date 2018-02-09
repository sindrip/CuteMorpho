func test(x1) {
	// vardecl
	var x;
	
	// expr
	x = 1;

	if (x) {
		x = false;
	} else if (x) {
		x = true;
	};
}

func test2(x, y, z) {
	// vardecl
	var x, y, z;

	// expr
	x = true;
	y = 'y';
	z = "flot";
	x = 2.34e-5;

	if (x) {
		x = false;
	};
}