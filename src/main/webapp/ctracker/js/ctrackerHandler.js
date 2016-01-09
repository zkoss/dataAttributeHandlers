/**
 * Data handler between ZK and jquery.ctracker.js.
 * Where jquery.ctracker.js is a JQuery UI Widget based on svg.js 3rd party library
 * See http://svgjs.com/
 *
 * @author Enrico Tedeschini (etedeschini@gmail.com)
 */
function (wgt, dataValue) {

    var self = this;
    
    // see index.zul <textbox ... ca:data-zkctracker="{iconsDistance: 180, maxCharsPerLine: 20}" />
    var settings = dataValue && dataValue.length > 0 ? jq.evalJSON(dataValue) : {};

    // get DOM handle
    var dom = wgt.$n();

    // decode value
    var dataObj  = JSON.parse(wgt.getValue());

    // initialize ctracker
    wgt._$handle = jq(dom).ctracker({
        data: dataObj,
        width: wgt.getWidth() || 300,			// I try to inherit width from zk component 
        height: wgt.getHeight() || 300,			// I try to inherit height from zk component 
        tabindex: wgt.getTabindex() || 0,		// I try to inherit tab order from zk component 
        iconsDistance: settings.iconsDistance || 180,
        maxCharsPerLine: settings.maxCharsPerLine || 30,

        // Events to server.
        // In CTrackerViewModel.java (MVVM) you have:
        // - this annotation of class @ToServerCommand({"zkctracker$onClickUser", "zkctracker$onSelectMessage", "zkctracker$onDeleteMessage", "zkctracker$onMoveMessage"})
        //   Annotation required to build the connection between server and client (Trigger commands in server by client).
        // - and these other annotations of method @Command("zkctracker$onClickUser"), @Command("zkctracker$onSelectMessage"), @Command("zkctracker$onDeleteMessage") and and @Command("zkctracker$onMoveMessage")
        //   Annotation required to catch 'onClickUser',etc  events from client side.
        // Notice that these commands contain "zkctracker" as a prefix.
        
        
        onClickUser: function (event, data) {
            // if (console)
            //    console.log('onClickUser' + event);
            if (self.command)
                self.command("$onClickUser", {id: wgt.getId(), name: data.label});
        },
        onSelectedMessage: function (event, data) {
            //if (console)
            //    console.log('onSelectedMessage' + event);
            if (self.command)
                self.command("$onSelectMessage", {id: wgt.getId(), messageId: data.id});
        },
        onDeleteMessage: function (event, data) {
            //if (console)
            //    console.log('onDeleteMessage' + event);
            if (self.command)
                self.command("$onDeleteMessage", {id: wgt.getId(), messageId: data.id});
        },
        onMoveMessage: function (event, data) {
            //if (console)
            //    console.log('onMoveMessage' + event);
            if (self.command)
                self.command("$onMoveMessage", {id: wgt.getId(), messageId: data.id, from: data.from, y: data.y});
        }
    });

    // example of how to resize the widget
    //wgt._$handle.ctracker("resize", 350, 350);

    // example of how to change distance among user icons
    //wgt._$handle.ctracker("iconsDistance", 200);

    var setValueChat = function (value) {

        // Parse data
        // For example : {
		//   "messages": [
		//     { "id": 100001, "time":"Sun Dec 27 09:36:01 CET 2015",  "from":"David", "text":"Good afternoon. Can I help you?" },
		//     { "id": 100002, "time":"Sun Dec 27 09:36:47 CET 2015", "from":"Elena",  "text":"Yes, please. We'd like to check in." }
		//   ],
        //   "users": [
        //     { "id": "David", "image": "images/user_male1.png" }
        //     { "id": "Elena", "image": "user_female.png" }
        //   ]}
        var dataObj  = JSON.parse(value);

        wgt._$handle.ctracker("setValue", dataObj);
    };

    wgt.setOverride("setValue", setValueChat);
}
