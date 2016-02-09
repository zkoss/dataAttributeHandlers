function handlerInit(wgt, dataValue) {
	/** Get the content of data value, or empty JSON if it doesn't exist */
	var dataObj = dataValue.length > 0 ? $.evalJSON(dataValue) : {};
	/** Set ArborJS parameters based on those values */
	var sys = arbor.ParticleSystem(dataObj.repulsion, dataObj.stiffness, dataObj.friction, dataObj.gravity, dataObj.fps, dataObj.dt, dataObj.precision)
	sys.parameters({gravity:true});
	sys.renderer = Renderer("#viewport") ;
	/** Create default nodes and edges, unrelated to the data handler */
	var data = {
	    	'color': 'green',
			'fixed': true,
			'label': 'ZK Tree root',
			'mass': '1',
			'shape': 'dot'
	 };
	sys.addNode('0',data);
	
	var data = {
	    	'color': 'grey',
			'fixed': false,
			'label': 'About',
			'mass': '1',
			'shape': 'dot'
	 };
	sys.addNode('about',data);
	
	var data = {
			'color': '#cc7700',
			'fixed': false,
			'label': 'Go to arborjs.org for the Arbor JS library',
			'mass': '1',
			'shape': 'box'
	};
	sys.addNode('about-credits',data);
	var data = {
			'color': '#aa9900',
			'fixed': false,
			'label': 'Seriously, go check it out. it\'s super cool!',
			'mass': '1',
			'shape': 'box'
	};
	sys.addNode('about-credits-seriously',data);
	var data = {
			'color': '#aa9900',
			'fixed': false,
			'label': 'They made this amazing dynamic visual',
			'mass': '1',
			'shape': 'box'
	};
	sys.addNode('about-credits-credits2',data);
	sys.addEdge('about','about-credits');
	sys.addEdge('about-credits','about-credits-seriously');
	sys.addEdge('about-credits','about-credits-credits2');
	sys.addEdge('0','about');
	/** end of init*/
	/** start the ArborJS system*/
	sys.start();
	var self = this;
	if (self.after) {
		/** add listeners for server commands. evt hold the value of the transfer property*/
		
		/**  server command for new child nodes. evt contain the node and the parent */
		self.after('$doClientAddChildNode', function (evt) {
			if (evt != null) {
				/**  set node properties */
				var data = {
					    	'color': evt.node.color,
							'fixed': evt.node.fixed,
							'label': evt.node.label,
							'mass': evt.node.mass,
							'shape': evt.node.shape
					 };
				/**  create the node */
				sys.addNode(evt.node.name,data);
				/**  create and edge between the node and its parent */
				sys.addEdge(evt.parent.name,evt.node.name,null);
			}
		});
		
		/** server command for node removal. evt is an array of node to remove*/
		self.after('$doClientRemoveNode', function (evt) {
			if (evt != null) {
				evt.forEach(function(entry) {
					/** prune remove nodes and edges related to the node*/
					sys.pruneNode(sys.getNode(entry.name));
					}
				)
			}
		});
		
	}
}