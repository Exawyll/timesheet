import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { TimesheetTestModule } from '../../../test.module';
import { WeekUpdateComponent } from 'app/entities/week/week-update.component';
import { WeekService } from 'app/entities/week/week.service';
import { Week } from 'app/shared/model/week.model';

describe('Component Tests', () => {
  describe('Week Management Update Component', () => {
    let comp: WeekUpdateComponent;
    let fixture: ComponentFixture<WeekUpdateComponent>;
    let service: WeekService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TimesheetTestModule],
        declarations: [WeekUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(WeekUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(WeekUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WeekService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Week(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Week();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
