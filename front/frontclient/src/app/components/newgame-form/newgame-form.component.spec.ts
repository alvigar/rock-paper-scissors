import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewGameFormComponent } from './newgame-form.component';

describe('UserFormComponent', () => {
  let component: NewGameFormComponent;
  let fixture: ComponentFixture<NewGameFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewGameFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewGameFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
