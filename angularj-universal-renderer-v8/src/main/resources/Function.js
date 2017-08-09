function renderPage(rendermodulefactory, appservermodulengfactory, uuid, template, uri) {
    rendermodulefactory(appservermodulengfactory, {document: template, url: uri}).then(function (html) {
        receiveRenderedPage(uuid, html, null);
    }).catch(function (error) {
        receiveRenderedPage(uuid, null, error);
    });
}
