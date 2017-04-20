import {SpringularPage} from "./app.po";

describe("springular App", () => {
    let page: SpringularPage;

    beforeEach(() => {
        page = new SpringularPage();
    });

    it("should display message saying app works", () => {
        page.navigateTo();
        expect(page.getParagraphText()).toEqual("app works!");
    });
});
