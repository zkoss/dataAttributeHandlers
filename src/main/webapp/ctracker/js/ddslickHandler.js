/**
 * Data handler between ZK and ddslick.js 3rd party library
 * 
 * See  http://designwithpc.com/Plugins/ddSlick
 *
 * @author Enrico Tedeschini (etedeschini@gmail.com)
 */
function (wgt, dataValue) {

    var self = this;
    
    var setValueDdslick = function (value) {

    	wgt.$setValue("");

       	if (wgt._$old) {

       	    // NOTE:
       	    // It's not possible to update the ddSlick list dynamically, 
       		// ddSlick widget cannot allow this. 
       	    // For this reason I destroy and re=create the ddslick again

       	    var $widget = jq('#' + wgt._$old.attr('id'));
       	    var parent = $widget.parent();
            $widget.ddslick('destroy');
            parent.append(wgt._$old);
            wgt._$old = undefined;
      	}

        // Parse data
        // For example : {
        //  placeHolder: "Select something, ...",
        //	data:[{ text: "Facebook", value: 1, selected: false, description: "Description with Facebook", imageSrc: "images/facebook-icon-32.png"},
        //	      { text: "Twitter",  value: 2, selected: false, description: "Description with Twitter",  imageSrc: "images/twitter-icon-32.png"},
        //	      { text: "LinkedIn", value: 3, selected: false, description: "Description with LinkedIn", imageSrc: "images/linkedin-icon-32.png"}
        //	      ]}
        var dataObj  = JSON.parse(value);

       	// get DOM handle  
        var dom = wgt.$n();

        // initialize the ddslick
        wgt._$old = jq(dom).ddslick({
            data: dataObj.data,
            width: 250,
            imagePosition: "left",
            selectText: dataObj.placeHolder,

            // Events to server.
            // In CTrackerViewModel.java (MVVM) you have: 
            // - this annotation of class @ToServerCommand({"zkddslick$onSelect"})
            //   Annotation required to build the connection between server and client (Trigger command in server by client).
            // - and this other annotation of method @Command("zkddslick$onSelect")
            //   Annotation required to catch 'onSelect' event from client side.
            // Notice that this command contains "zkddslick" as a prefix.
            
            onSelected: function (data) {

                //if (console)
                //    console.log('onSelect' + data);
            	if (self.command)
                    self.command("$onSelect", {id: wgt.getId(), index: data.selectedIndex});
            }
        });  

        // Example about how to focus highlighting ddslick in active state. 
        // Not verified with all browsers and all platforms
        // jq('#' + wgt._$old.attr('id'))
        //    .addClass('form-control focusedInput')
        //    .attr('tabindex', 0)
        //    .css('height' ,'inherit');

    };

    wgt.setOverride("setValue", setValueDdslick);

    setValueDdslick(wgt.getValue());
}
