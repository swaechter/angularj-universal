import {AppPage} from "./app.po";

describe("angular App", () => {
    let page: AppPage;

    beforeEach(() => {
        page = new AppPage();
    });

    it("should display login message", () => {
        page.navigateTo();
        expect(page.getParagraphText()).toEqual("Login");
    });
});
